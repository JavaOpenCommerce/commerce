package com.example.javaopencommerce.order;

import lombok.Value;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

@Value
class Amount {

    int value;

    private Amount(int amount) {
        this.value = amount;
    }

    public static Amount of(int amount) {
        if (amount < 1) {
            amount = 1;
        }
        return new Amount(amount);
    }

    public BigDecimal asDecimal() {
        return valueOf(this.value);
    }

    public int asInteger() {
        return this.value;
    }
}
