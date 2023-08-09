package com.example.javaopencommerce.order;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.ItemId;
import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.order.CardItem.CardItemSnapshot;
import com.example.javaopencommerce.order.Item.ItemSnapshot;
import com.example.javaopencommerce.order.exceptions.ordervalidation.OrderValidationException;
import com.example.javaopencommerce.order.exceptions.ordervalidation.OrderValueNotMatchingValidationException;
import com.example.javaopencommerce.order.exceptions.ordervalidation.OutOfStockItemsValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

class OrderIntegrityValidator {

    private static void validateOrderSize(CardSnapshot cardToValidate, CardSnapshot expected) {
        if (cardToValidate.items()
                .size() != expected.items()
                .size()) {
            throw new OrderValidationException(
                    String.format("Order body is different than actual card body! Order items: %s, card items: %s",
                            cardToValidate.items()
                                    .stream()
                                    .map(CardItemSnapshot::id)
                                    .map(ItemId::asLong)
                                    .toList(),
                            expected.items()
                                    .stream()
                                    .map(CardItemSnapshot::id)
                                    .map(ItemId::asLong)
                                    .toList()));
        }
    }

    void validate(CardSnapshot cardToValidate, CardSnapshot expected) {
        List<OrderValidationException> orderInaccuracies = new ArrayList<>();

        validateOrderSize(cardToValidate, expected);

        orderInaccuracies.addAll(validateOrderGrossValue(cardToValidate, expected));
        orderInaccuracies.addAll(validateOrderNettValue(cardToValidate, expected));
        orderInaccuracies.addAll(validateStocks(cardToValidate.items(), expected.items()));

        if (!orderInaccuracies.isEmpty()) {
            throw new OrderValidationException(orderInaccuracies);
        }
    }

    private List<OrderValidationException> validateOrderGrossValue(CardSnapshot toValidate,
                                                                   CardSnapshot expected) {
        Map<ItemId, Value> currentItemGrossPrices = expected.items()
                .stream()
                .filter(Objects::nonNull)
                .collect(toMap(CardItemSnapshot::id, cardItem -> cardItem.itemSnapshot()
                        .valueGross()));

        Value currentOrderValueGross = calculateCurrentOrderGrossValue(toValidate.items(),
                currentItemGrossPrices);

        if (!currentOrderValueGross.equals(toValidate.cardValueGross())) {
            return List.of(
                    new OrderValueNotMatchingValidationException("gross", toValidate.cardValueGross(),
                            currentOrderValueGross));
        }
        return emptyList();
    }

    private List<OrderValidationException> validateOrderNettValue(CardSnapshot toValidate,
                                                                  CardSnapshot expected) {
        Map<ItemId, Value> currentItemGrossPrices = expected.items()
                .stream()
                .filter(Objects::nonNull)
                .collect(toMap(CardItemSnapshot::id, cardItem -> cardItem.itemSnapshot()
                        .valueGross()));

        Value currentOrderValueNett = calculateCurrentOrderNettValue(toValidate.items(), currentItemGrossPrices);

        if (!currentOrderValueNett.equals(toValidate.cardValueNett())) {
            return List.of(
                    new OrderValueNotMatchingValidationException("nett", toValidate.cardValueNett(),
                            currentOrderValueNett));
        }
        return emptyList();
    }

    private Value calculateCurrentOrderGrossValue(List<CardItemSnapshot> orderedItems,
                                                  Map<ItemId, Value> currentPrices) {
        return orderedItems.stream()
                .filter(item -> !item.amount()
                        .isZero())
                .map(
                        item -> ofNullable(currentPrices.get(item.id())).orElseThrow(
                                        () -> new OrderValidationException(
                                                String.format("Price for item with id: %s, not found!", item.id())))
                                .multiply(item.amount()))
                .reduce(Value.ZERO, Value::add);
    }

    private Value calculateCurrentOrderNettValue(List<CardItemSnapshot> orderedItems,
                                                 Map<ItemId, Value> currentPrices) {
        return orderedItems.stream()
                .filter(item -> !item.amount()
                        .isZero())
                .map(
                        item -> ofNullable(currentPrices.get(item.id())).orElseThrow(
                                        () -> new OrderValidationException(
                                                String.format("Price for item with id: %s, not found!", item.id())))
                                .multiply(item.amount())
                                .toNett(item.itemSnapshot()
                                        .vat()))
                .reduce(Value.ZERO, Value::add);
    }

    private List<OutOfStockItemsValidationException> validateStocks(
            List<CardItemSnapshot> itemsToValidate, List<CardItemSnapshot> expectedItems) {

        Map<ItemId, Amount> currentStocks = expectedItems.stream()
                .map(CardItemSnapshot::itemSnapshot)
                .collect(toMap(ItemSnapshot::id, ItemSnapshot::stock));

        List<CardItemSnapshot> outOfStockItems = itemsToValidate.stream()
                .filter(product -> !isEnoughInStock(currentStocks, product))
                .toList();

        if (!outOfStockItems.isEmpty()) {
            return List.of(
                    new OutOfStockItemsValidationException(
                            outOfStockItems.stream()
                                    .map(CardItemSnapshot::id)
                                    .map(ItemId::asLong)
                                    .map(String::valueOf)
                                    .collect(
                                            Collectors.joining(", "))));
        }
        return emptyList();
    }

    private boolean isEnoughInStock(Map<ItemId, Amount> currentStocks, CardItemSnapshot orderedItem) {
        Amount currentStock = ofNullable(currentStocks.get(orderedItem.id())).orElseThrow(
                () -> new OrderValidationException(
                        String.format("Stock for item with id: %s, not found!", orderedItem.id())));
        return orderedItem.amount()
                .isLessOrEqualThan(currentStock);
    }
}
