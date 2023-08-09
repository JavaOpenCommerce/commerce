package com.example.javaopencommerce.order;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.ItemId;
import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.order.Item.ItemSnapshot;

import static java.math.BigDecimal.ZERO;
import static java.util.Objects.requireNonNull;

final class CardItem {

    private final Item item;
    private Amount amount;
    private Value valueNett = Value.of(ZERO);
    private Value valueGross = Value.of(ZERO);
    private CardItemStatus status;

    private CardItem(Item item, Amount amount, CardItemStatus status) {
        this.item = item;
        this.amount = amount;
        this.status = status;
        calculateSumValue();
    }

    static CardItem empty(ItemId id, String name) {
        return new CardItem(Item.empty(id, name), Amount.ZERO, CardItemStatus.ITEM_NO_LONGER_EXISTS);
    }

    static CardItem withAmount(Item item, Amount amount) {
        requireNonNull(item);
        requireNonNull(amount);

        if (item.getStock()
                .isLessThan(amount)) {
            return new CardItem(item, item.getStock(), CardItemStatus.NOT_ENOUGH_IN_STOCK);
        }

        return new CardItem(item, amount, CardItemStatus.OK);
    }

    void increaseAmountBy(Amount itemsToAdd) {
        if (item.getStock()
                .isLessThan(this.amount.plus(itemsToAdd))) {
            this.amount = item.getStock();
            this.status = CardItemStatus.NOT_ENOUGH_IN_STOCK;
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

    boolean hasZeroAmount() {
        return this.amount.isZero();
    }

    CardItemSnapshot getSnapshot() {
        return new CardItemSnapshot(this.item.getSnapshot(), valueNett, valueGross, amount,
                status.ok() ? "OK" : status.getCause()
                        .name());
    }

    private void calculateSumValue() {
        valueGross = item.getValueGross()
                .multiply(amount.asDecimal());
        valueNett = item.getVat()
                .toNett(valueGross);
    }

    record CardItemSnapshot(ItemSnapshot itemSnapshot, Value valueNett, Value valueGross,
                            Amount amount, String status) {

        ItemId id() {
            return itemSnapshot.id();
        }
    }
}
