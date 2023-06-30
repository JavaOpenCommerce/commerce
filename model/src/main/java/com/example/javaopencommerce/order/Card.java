package com.example.javaopencommerce.order;

import static java.math.BigDecimal.ZERO;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.Value;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode
final class Card {

  private final Map<ItemId, CardItem> items;
  private Value cardValueNett = Value.of(ZERO);
  private Value cardValueGross = Value.of(ZERO);

  private Card(Map<ItemId, CardItem> productsMap) {
    this.items = new HashMap<>(productsMap);
    calculateCardValue();
  }

  static Card ofProducts(List<CardItem> cardItems) {
    Map<ItemId, CardItem> card = cardItems.stream()
        .collect(Collectors.toMap(CardItem::id, Function.identity()));
    return new Card(card);
  }

  static Card validateAndRecreate(CardSnapshot cardToValidate, List<CardItem> cardItems) {
    Map<ItemId, CardItem> card = cardItems.stream().filter(item -> !item.hasZeroAmount())
        .collect(Collectors.toMap(CardItem::id, Function.identity()));

    Card actualCard = new Card(card);

    new OrderIntegrityValidator().validate(cardToValidate, actualCard.getSnapshot());

    return actualCard;
  }

  Order createOrderFor(OrderPrincipal principal) {
    calculateCardValue();
    return Order.newOrder(getSnapshot(), principal);
  }

  void addItem(Item item, Amount amount) {
    ItemId itemId = item.getId();
    if (this.items.containsKey(itemId)) {
      items.get(itemId).increaseAmountBy(amount);
    } else {
      items.put(itemId, CardItem.withAmount(item, amount));
    }
    calculateCardValue();
  }

  void changeItemAmount(Item item, Amount amount) {
    ItemId itemId = item.getId();
    this.items.remove(itemId);
    if (amount.isZero()) {
      return;
    }
    items.put(itemId, CardItem.withAmount(item, amount));
    calculateCardValue();
  }

  void removeItem(ItemId itemId) {
    if (this.items.containsKey(itemId)) {
      items.remove(itemId);
    }
    calculateCardValue();
  }

  List<CardItem> getCardItems() {
    return new ArrayList<>(items.values());
  }

  CardSnapshot getSnapshot() {
    calculateCardValue();
    return CardSnapshot.builder()
        .items(new ArrayList<>(items.values().stream().map(CardItem::getSnapshot).toList()))
        .cardValueGross(cardValueGross).cardValueNett(cardValueNett).build();
  }

  private void calculateCardValue() {
    this.cardValueGross = this.items.values().stream().map(CardItem::valueGross)
        .reduce(Value.zero(), Value::add);
    this.cardValueNett = this.items.values().stream().map(CardItem::valueNett)
        .reduce(Value.zero(), Value::add);
  }
}
