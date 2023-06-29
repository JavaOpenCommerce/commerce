package com.example.javaopencommerce.order;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.requireNonNull;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.order.Item.ItemSnapshot;
import com.example.javaopencommerce.order.exceptions.OutOfStockException;

final class CardItem {

  private final Item item;
  private Amount amount;
  private Value valueNett = Value.of(ZERO);
  private Value valueGross = Value.of(ZERO);

  private CardItem(Item item, Amount amount) {
    this.item = item;
    this.amount = amount;
    calculateSumValue();
  }

  static CardItem withAmount(Item item, Amount amount) {
    requireNonNull(item);
    requireNonNull(amount);

    if (item.getStock().isLessThan(amount)) {
      throw new OutOfStockException(item.getStock(), item.getStock().plus(amount));
    }

    return new CardItem(item, amount);
  }

  void increaseAmountBy(Amount itemsToAdd) {
    if (item.getStock().isLessThan(this.amount.plus(itemsToAdd))) {
      throw new OutOfStockException(item.getStock(), item.getStock().plus(itemsToAdd));
    }
    this.amount = this.amount.plus(itemsToAdd);
    calculateSumValue();
  }

  ItemId id() {
    return this.item.getId();
  }

  Value valueNett() {
    return valueNett;
  }

  Value valueGross() {
    return valueGross;
  }

  CardItemSnapshot getSnapshot() {
    return new CardItemSnapshot(this.item.getSnapshot(), valueNett, valueGross, amount);
  }

  private void calculateSumValue() {
    valueGross = item.getValueGross().multiply(amount.asDecimal());
    valueNett = valueGross.divide(item.getVat().asDecimal().add(ONE));
  }

  record CardItemSnapshot(ItemSnapshot itemSnapshot, Value valueNett, Value valueGross,
                          Amount amount) {

    ItemId id() {
      return itemSnapshot.id();
    }
  }
}
