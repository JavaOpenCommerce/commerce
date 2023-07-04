package com.example.javaopencommerce.order;

import com.example.javaopencommerce.Amount;
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

    void validate(CardSnapshot cardToValidate, CardSnapshot expected) {
        List<OrderValidationException> orderInaccuracies = new ArrayList<>();

        orderInaccuracies.addAll(validateOrderGrossValue(cardToValidate, expected));
        orderInaccuracies.addAll(validateOrderNettValue(cardToValidate, expected));
        orderInaccuracies.addAll(validateStocks(cardToValidate.items(), expected.items()));

        if (!orderInaccuracies.isEmpty()) {
            throw new OrderValidationException(orderInaccuracies);
        }
    }

    private List<OrderValidationException> validateOrderGrossValue(CardSnapshot toValidate,
                                                                   CardSnapshot expected) {
        Map<ItemId, Value> currentItemPrices = expected.items()
                .stream()
                .filter(Objects::nonNull)
                .collect(toMap(CardItemSnapshot::id, CardItemSnapshot::valueGross));

        Value currentOrderValueGross = calculateCurrentOrderValue(toValidate.items(),
                currentItemPrices);

        if (!currentOrderValueGross.equals(toValidate.cardValueGross())) {
            return List.of(
                    new OrderValueNotMatchingValidationException("gross", toValidate.cardValueGross(),
                            currentOrderValueGross));
        }
        return emptyList();
    }

    private List<OrderValidationException> validateOrderNettValue(CardSnapshot toValidate,
                                                                  CardSnapshot expected) {
        Map<ItemId, Value> currentItemPrices = expected.items()
                .stream()
                .filter(Objects::nonNull)
                .collect(toMap(CardItemSnapshot::id, CardItemSnapshot::valueNett));

        Value currentOrderValueNett = calculateCurrentOrderValue(toValidate.items(), currentItemPrices);

        if (!currentOrderValueNett.equals(toValidate.cardValueNett())) {
            return List.of(
                    new OrderValueNotMatchingValidationException("nett", toValidate.cardValueNett(),
                            currentOrderValueNett));
        }
        return emptyList();
    }

    private Value calculateCurrentOrderValue(List<CardItemSnapshot> orderedProducts,
                                             Map<ItemId, Value> currentPrices) {
        return orderedProducts.stream()
                .map(
                        item -> ofNullable(currentPrices.get(item.id())).orElseThrow(
                                        () -> new OrderValidationException(
                                                String.format("Price for item with id: %s, not found!", item.id())))
                                .multiply(item.amount()))
                .reduce(Value.ZERO, Value::add);
    }

    private List<OutOfStockItemsValidationException> validateStocks(
            List<CardItemSnapshot> currentItems, List<CardItemSnapshot> orderedItems) {

        Map<ItemId, Amount> currentStocks = currentItems.stream()
                .map(CardItemSnapshot::itemSnapshot)
                .collect(toMap(ItemSnapshot::id, ItemSnapshot::stock));

        List<CardItemSnapshot> outOfStockItems = orderedItems.stream()
                .filter(product -> !isEnoughInStock(currentStocks, product))
                .toList();

        if (!outOfStockItems.isEmpty()) {
            return List.of(
                    new OutOfStockItemsValidationException(
                            outOfStockItems.stream()
                                    .map(CardItemSnapshot::id)
                                    .map(ItemId::id)
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
                .isLessThan(currentStock);
    }
}
