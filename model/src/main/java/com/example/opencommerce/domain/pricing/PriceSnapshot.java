package com.example.opencommerce.domain.pricing;

import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.Value;
import com.example.opencommerce.domain.Vat;

import static java.util.Objects.requireNonNull;

public class PriceSnapshot {
    private final ItemId itemId;
    private final Value basePriceNett;
    private final Value basePriceGross;
    private final Discount discount;

    private PriceSnapshot(ItemId itemId, Value basePriceNett, Value basePriceGross, Discount discount) {
        this.itemId = requireNonNull(itemId);
        this.basePriceNett = requireNonNull(basePriceNett);
        this.basePriceGross = requireNonNull(basePriceGross);
        this.discount = discount;
    }

    static PriceSnapshot discountedPrice(ItemId itemId, Value basePriceNett, Value discountValue, Value lowestPriceBeforeDiscountGross, Vat vat) {
        Value discountedPriceNett = basePriceNett.subtract(discountValue);
        Value discountedPriceGross = discountedPriceNett.toGross(vat);
        Discount discount = new Discount(discountedPriceNett, discountedPriceGross, lowestPriceBeforeDiscountGross);
        Value basePriceGross = basePriceNett.toGross(vat);

        return new PriceSnapshot(itemId, basePriceNett, basePriceGross, discount);
    }

    static PriceSnapshot regularPrice(ItemId itemId, Value basePriceNett, Vat vat) {
        Value basePriceGross = basePriceNett.toGross(vat);

        return new PriceSnapshot(itemId, basePriceNett, basePriceGross, null);
    }

    public ItemId getItemId() {
        return itemId;
    }

    public Value getBasePriceNett() {
        return basePriceNett;
    }

    public Value getBasePriceGross() {
        return basePriceGross;
    }

    public Discount getDiscount() {
        return discount;
    }

    public record Discount(
            Value discountedPriceNett,
            Value discountedPriceGross,
            Value lowestPriceBeforeDiscountGross) {
    }
}
