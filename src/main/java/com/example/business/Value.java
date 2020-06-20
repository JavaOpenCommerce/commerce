package com.example.business;

import java.math.BigDecimal;

public final class Value {

    private final BigDecimal value;

    private Value(BigDecimal value) {this.value = value;}

    public static Value of(BigDecimal value) {
        if (value.signum() < 0) {
            value = BigDecimal.valueOf(0L, 2);
        }
        return new Value(value);
    }

    public BigDecimal asDecimal() {
        return value;
    }

    public Value asObject() {
        return this;
    }
}
