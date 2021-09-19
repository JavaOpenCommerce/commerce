package com.example.javaopencommerce.order;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.item.Item;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public final class Product {

    private final Item itemModel;
    private Value valueNett = Value.of(ZERO);
    private Value valueGross = Value.of(ZERO);
    private Amount amount;


    private Product(Item itemModel, int amount) {
        this.itemModel = itemModel;
        this.amount = Amount.of(amount);
        calculateSumValue();
    }

    private Product(Item itemModel) {
        this(itemModel, 1);
    }

    public static Product getProduct(Item itemModel) {
        return new Product(itemModel);
    }

    public static Product getProduct(Item itemModel, int amount) {
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
