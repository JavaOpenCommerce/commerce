package com.example.javaopencommerce;

import lombok.Value;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

@Value
public class Amount {

    public final static Amount ZERO = new Amount(0);

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

    public Amount plus(Amount amount) {
        return new Amount(value + amount.getValue());
    }

    public Amount minusOne() {
        int newAmount = value <= 0 ? 0 : value - 1;
        return new Amount(newAmount);
    }

    public boolean isLessThan(Amount amount) {
        return value < amount.getValue();
    }

    public boolean isZero() {
        return value <= 0;
    }

    public int asInteger() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
