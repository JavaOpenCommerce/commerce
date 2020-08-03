package com.example.business;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public final class Value {

    private final BigDecimal value;

    private Value(BigDecimal value) {this.value = value;}

    public static Value of(BigDecimal value) {
        if (value == null || value.signum() < 0) {
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

    public Value multiply(BigDecimal multiplier) {
        return Value.of(this.value.multiply(multiplier, MathContext.DECIMAL32));
    }

    public Value divide(BigDecimal divider) {
        return Value.of(value.divide(divider, 2, RoundingMode.HALF_UP));
    }
}
