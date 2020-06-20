package com.example.business;

import com.example.business.models.ItemModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.MathContext;
import java.math.RoundingMode;

import static java.math.BigDecimal.ONE;

@Getter
@EqualsAndHashCode
public final class Product {

    private final ItemModel itemModel;
    private Value valueNett;
    private Value valueGross;
    private Amount amount;


    private Product(ItemModel itemModel) {
        this(itemModel, 1);
    }

    private Product(ItemModel itemModel, int amount) {
        this.itemModel = itemModel;
        this.amount = Amount.of(amount);
        calculateSumValue();
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
        valueGross = Value.of(itemModel.getValueGross().asDecimal().multiply(amount.asDecimal(), MathContext.DECIMAL32));
        valueNett = Value.of(valueGross.asDecimal().divide(itemModel.getVat().asDecimal().add(ONE), 2, RoundingMode.HALF_UP));
    }
}
