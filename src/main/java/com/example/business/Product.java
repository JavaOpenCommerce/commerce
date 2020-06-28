package com.example.business;

import com.example.business.models.ItemModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

@Getter
@EqualsAndHashCode
public final class Product {

    private final ItemModel itemModel;
    private Value valueNett = Value.of(ZERO);
    private Value valueGross = Value.of(ZERO);
    private Amount amount;


    private Product(ItemModel itemModel, int amount) {
        this.itemModel = itemModel;
        this.amount = Amount.of(amount);
        calculateSumValue();
    }

    private Product(ItemModel itemModel) {
        this(itemModel, 1);
    }

    public static Product getProduct(ItemModel itemModel) {
        return new Product(itemModel);
    }

    public static Product getProduct(ItemModel itemModel, int amount) {
        return new Product(itemModel, amount);
    }

    public void setAmount(int amount) {
        this.amount = Amount.of(amount);
        calculateSumValue();
    }

    private void calculateSumValue() {
        valueGross = itemModel.getValueGross().multiply(amount.asDecimal());
        valueNett = valueGross.divide(itemModel.getVat().asDecimal().add(ONE));
    }
}
