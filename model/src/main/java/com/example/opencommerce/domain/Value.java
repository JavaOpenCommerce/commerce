package com.example.opencommerce.domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Value {

    public static final Value ZERO = new Value(BigDecimal.valueOf(0, 2));

    private final BigDecimal value;

    private Value(BigDecimal value) {
        if (value.scale() > 2) {
            throw new IllegalStateException(String.format("Scale for any value should not be higher than 2! Current scale: %s", value.scale()));
        }
        this.value = value.setScale(2, RoundingMode.HALF_UP);
    }

    public static Value of(BigDecimal value) {
        requireNonNull(value);
        return new Value(value);
    }

    public static Value of(Double value) {
        requireNonNull(value);
        return new Value(BigDecimal.valueOf(value));
    }

    public BigDecimal asDecimal() {
        return this.value;
    }

    public Value multiply(BigDecimal multiplier) {
        return Value.of(this.value.multiply(multiplier, MathContext.DECIMAL32)
                .setScale(2, RoundingMode.HALF_UP));
    }

    public Value multiply(Amount multiplier) {
        return Value.of(this.value.multiply(multiplier.asDecimal(), MathContext.DECIMAL32)
                .setScale(2, RoundingMode.HALF_UP));
    }

    public Value divide(BigDecimal divider) {
        return Value.of(this.value.divide(divider, 2, RoundingMode.HALF_UP));
    }

    public Value add(Value toAdd) {
        return Value.of(this.value.add(toAdd.value));
    }

    public Value subtract(Value toSubtract) {
        return Value.of(this.value.subtract(toSubtract.value));
    }

    public boolean isNegative() {
        return value.signum() < 0;
    }

    public Value toGross(Vat vat) {
        BigDecimal multiplier = BigDecimal.ONE.add(vat.asDecimal());
        return this.multiply(multiplier);
    }

    public Value toNett(Vat vat) {
        BigDecimal divider = BigDecimal.ONE.add(vat.asDecimal());
        return this.divide(divider);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Value value1 = (Value) o;
        return Objects.equals(value, value1.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
