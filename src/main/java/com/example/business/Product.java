package com.example.business;

import com.example.business.models.ItemModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.valueOf;

@Getter
@EqualsAndHashCode
public final class Product {

    private final ItemModel itemModel;
    private BigDecimal valueNett;
    private BigDecimal valueGross;
    private int amount = 1;


    private Product(ItemModel itemModel) {
        this.itemModel = itemModel;
        calculateSumValue();
    }

    public static Product getProduct(ItemModel itemModel) {
        return new Product(itemModel);
    }

    public void setAmount(int amount) {
        this.amount = amount;
        calculateSumValue();
    }

    private void calculateSumValue() {
        valueGross = itemModel.getValueGross().multiply(valueOf(amount));
        valueNett = valueGross.divide(itemModel.getVat().add(ONE));
    }
}
