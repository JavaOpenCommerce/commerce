package com.example.opencommerce.domain.order;

import com.example.opencommerce.domain.Amount;
import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.Value;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@EqualsAndHashCode
public final class Card {

    private final Map<ItemId, CardItem> items;
    private Value cardValueNett = Value.ZERO;
    private Value cardValueGross = Value.ZERO;

    private Card(Map<ItemId, CardItem> productsMap) {
        this.items = new HashMap<>(productsMap);
        calculateCardValue();
    }

    public static Card ofProducts(List<CardItem> cardItems) {
        Map<ItemId, CardItem> card = cardItems.stream()
                .collect(Collectors.toMap(CardItem::id, Function.identity()));
        return new Card(card);
    }

    public static Card validateAndRecreate(CardSnapshot cardToValidate, List<CardItem> cardItems) {
        Map<ItemId, CardItem> card = cardItems.stream()
                .collect(Collectors.toMap(CardItem::id, Function.identity()));

        Card actualCard = new Card(card);

        new OrderIntegrityValidator().validate(cardToValidate, actualCard.getSnapshot());

        return actualCard;
    }

    public Order createOrderFor(OrderPrincipal principal) {
        calculateCardValue();
        Order order = Order.newOrder(getSnapshot(), principal);
        this.flush();
        return order;
    }

    public void addItem(Item item, Amount amount) {
        ItemId itemId = item.getId();
        if (this.items.containsKey(itemId)) {
            items.get(itemId)
                    .increaseAmountBy(amount);
        } else {
            items.put(itemId, CardItem.withAmount(item, amount));
        }
        calculateCardValue();
    }

    public void changeItemAmount(Item item, Amount amount) {
        ItemId itemId = item.getId();
        this.items.remove(itemId);
        if (amount.isZero()) {
            return;
        }
        items.put(itemId, CardItem.withAmount(item, amount));
        calculateCardValue();
    }

    public void removeItem(ItemId itemId) {
        if (this.items.containsKey(itemId)) {
            items.remove(itemId);
        }
        calculateCardValue();
    }

    public List<CardItem> getCardItems() {
        return new ArrayList<>(items.values());
    }

    public CardSnapshot getSnapshot() {
        calculateCardValue();
        return CardSnapshot.builder()
                .items(new ArrayList<>(items.values()
                        .stream()
                        .map(CardItem::getSnapshot)
                        .toList()))
                .cardValueGross(cardValueGross)
                .cardValueNett(cardValueNett)
                .build();
    }

    private void flush() {
        items.clear();
        calculateCardValue();
    }

    private void calculateCardValue() {
        this.cardValueGross = this.items.values()
                .stream()
                .map(CardItem::valueGross)
                .reduce(Value.ZERO, Value::add);
        this.cardValueNett = this.items.values()
                .stream()
                .map(CardItem::valueNett)
                .reduce(Value.ZERO, Value::add);
    }
}
