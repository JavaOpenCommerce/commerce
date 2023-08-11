package com.example.opencommerce.domain.order;

import com.example.opencommerce.domain.Amount;
import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.Value;
import com.example.opencommerce.domain.order.Item.ItemSnapshot;

import static java.math.BigDecimal.ZERO;
import static java.util.Objects.requireNonNull;

public final class CardItem {

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

    public static CardItem empty(ItemId id, String name) {
        return new CardItem(Item.empty(id, name), Amount.ZERO, CardItemStatus.ITEM_NO_LONGER_EXISTS);
    }

    public static CardItem withAmount(Item item, Amount amount) {
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

    public CardItemSnapshot getSnapshot() {
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

    public record CardItemSnapshot(ItemSnapshot itemSnapshot, Value valueNett, Value valueGross,
                                   Amount amount, String status) {

        public ItemId id() {
            return itemSnapshot.id();
        }
    }
}
