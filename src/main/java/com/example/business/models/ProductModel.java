package com.example.business.models;

import com.example.business.Amount;
import com.example.business.Value;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

@Getter
@EqualsAndHashCode
public final class ProductModel {

    private final ItemModel itemModel;
    private Value valueNett = Value.of(ZERO);
    private Value valueGross = Value.of(ZERO);
    private Amount amount;


    private ProductModel(ItemModel itemModel, int amount) {
        this.itemModel = itemModel;
        this.amount = Amount.of(amount);
        calculateSumValue();
    }

    private ProductModel(ItemModel itemModel) {
        this(itemModel, 1);
    }

    public static ProductModel getProduct(ItemModel itemModel) {
        return new ProductModel(itemModel);
    }

    public static ProductModel getProduct(ItemModel itemModel, int amount) {
        return new ProductModel(itemModel, amount);
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
