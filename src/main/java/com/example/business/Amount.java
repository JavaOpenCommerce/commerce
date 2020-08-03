package com.example.business;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

public final class Amount {

    private final int amount;

    private Amount(int amount) {
        this.amount = amount;
    }

    public static Amount of(int amount) {
        if (amount < 1) {
            amount = 1;
        }
        return new Amount(amount);
    }

    public BigDecimal asDecimal() {
        return valueOf(amount);
    }

    public int asInteger() {
        return amount;
    }
}
