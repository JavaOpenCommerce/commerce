package com.example.opencommerce.domain.pricing;

import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.OperationResult;
import com.example.opencommerce.domain.Value;
import com.example.opencommerce.domain.Vat;
import com.example.opencommerce.domain.pricing.events.*;

import java.time.Instant;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class ItemPrice {

    private static final String PRICE_NOT_INITIATED_MSG = "Price not initiated yet!";
    private final ItemId id;
    private final List<HistoricalPrice> history = new ArrayList<>();
    private Value basePriceNett;
    private Value discount;
    private Vat vat;
    private boolean initiated;

    private ItemPrice(ItemId id) {
        this.id = requireNonNull(id);
        this.basePriceNett = Value.ZERO;
        this.vat = Vat.ZERO;
        this.discount = Value.ZERO;
        this.initiated = false;
    }

    public static ItemPrice create(ItemId id) {
        return new ItemPrice(id);
    }

    public void when(List<PriceEvent> history) {
        history.forEach(this::apply);
    }

    public OperationResult<NewPriceInitiatedEvent> initiate(Value priceNett, Vat vat) {
        return executePriceInitiate(priceNett, vat);
    }

    public OperationResult<BasePriceChangedEvent> changeBasePrice(Value newPrice, Instant executionDate) {
        return executePriceChange(newPrice, executionDate);
    }

    public OperationResult<DiscountAppliedEvent> addNewDiscount(Value discount, Instant executionDate) {
        return executeDiscount(discount, executionDate);
    }

    public OperationResult<DiscountRemovedEvent> removeCurrentDiscount(Instant executionDate) {
        return executeDiscountRemoval(executionDate);
    }

    private Value lowestPriceWithinPeriod(Period timePeriod) {
        Instant startDate = Instant.now()
                .minus(timePeriod);
        return this.history.stream()
                .filter(price -> price.getValidTo()
                        .isAfter(startDate))
                .min(Comparator.comparing(p -> p.getPriceNett()
                        .asDecimal()))
                .map(HistoricalPrice::getPriceNett)
                .orElse(this.basePriceNett);
    }

    public PriceSnapshot getSnapshot() {
        if (discounted()) {
            return PriceSnapshot.discountedPrice(this.id, this.basePriceNett, this.discount, lowestPriceWithinPeriod(Period.ofDays(30)), this.vat);
        } else {
            return PriceSnapshot.regularPrice(this.id, this.basePriceNett, this.vat);
        }
    }

    private void apply(PriceEvent e) {

        OperationResult<?> result;
        if (e instanceof NewPriceInitiatedEvent event) {
            result = executePriceInitiate(Value.of(event.getBasePrice()), Vat.of(event.getVat()));
        } else if (e instanceof BasePriceChangedEvent event) {
            result = executePriceChange(Value.of(event.getNewPrice()), event.getValidFrom());
        } else if (e instanceof DiscountAppliedEvent event) {
            result = executeDiscount(Value.of(event.getDiscount()), event.getValidFrom());
        } else if (e instanceof DiscountRemovedEvent event) {
            result = executeDiscountRemoval(event.getValidFrom());
        } else {
            throw new IllegalStateException("Unknown price event type: " + e.getClass()
                    .getSimpleName());
        }

        if (!result.successful()) {
            throw new IllegalStateException(
                    format("Critical error during aggregate recovery! Problems: \n%s", String.join("\n", result.getErrors())));
        }
    }

    private OperationResult<NewPriceInitiatedEvent> executePriceInitiate(Value priceNett, Vat vat) {
        if (isInitiated()) {
            return OperationResult.failed("Can't perform initiation on already initiated price record!");
        }

        this.basePriceNett = priceNett;
        this.vat = vat;
        this.initiated = true;

        NewPriceInitiatedEvent event = new NewPriceInitiatedEvent(priceNett, vat);
        return OperationResult.success(event);
    }

    private OperationResult<BasePriceChangedEvent> executePriceChange(Value newPrice, Instant executionTime) {
        if (!isInitiated()) {
            return OperationResult.failed(PRICE_NOT_INITIATED_MSG);
        }

        if (newPrice.isNegative()) {
            return OperationResult.failed(format("New price: %s is negative!", newPrice));
        }

        if (newPrice.subtract(this.discount)
                .isNegative()) {
            return OperationResult.failed(
                    format("New price: %s with current discount: %s applied is negative: %s!", newPrice, this.discount, newPrice.subtract(this.discount)));
        }

        this.history.add(HistoricalPrice.at(executionTime, discountedPrice()));
        this.basePriceNett = newPrice;

        BasePriceChangedEvent event = new BasePriceChangedEvent(executionTime, newPrice);

        return OperationResult.success(event);
    }

    private OperationResult<DiscountAppliedEvent> executeDiscount(Value discount, Instant executionTime) {
        if (!isInitiated()) {
            return OperationResult.failed(PRICE_NOT_INITIATED_MSG);
        }

        if (discount.isNegative()) {
            return OperationResult.failed(format("Discount value: %s is negative!", discount));
        }

        if (this.basePriceNett.subtract(discount)
                .isNegative()) {
            return OperationResult.failed(
                    format("Discount value: %s is higher than current base price: %s!", discount, basePriceNett));
        }

        this.history.add(HistoricalPrice.at(executionTime, discountedPrice()));
        this.discount = discount;

        DiscountAppliedEvent event = new DiscountAppliedEvent(executionTime, discount);

        return OperationResult.success(event);
    }

    private OperationResult<DiscountRemovedEvent> executeDiscountRemoval(Instant executionTime) {
        if (!isInitiated()) {
            return OperationResult.failed(PRICE_NOT_INITIATED_MSG);
        }

        this.history.add(HistoricalPrice.at(executionTime, discountedPrice()));
        this.discount = Value.ZERO;

        DiscountRemovedEvent event = new DiscountRemovedEvent(executionTime);

        return OperationResult.success(event);
    }

    private boolean discounted() {
        return !this.discount.equals(Value.ZERO);
    }

    private boolean isInitiated() {
        return this.initiated;
    }

    private Value discountedPrice() {
        return this.basePriceNett.subtract(this.discount);
    }
}
