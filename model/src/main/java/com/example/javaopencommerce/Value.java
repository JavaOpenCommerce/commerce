package com.example.javaopencommerce;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@lombok.Value
public class Value {

  public static final Value ZERO = new Value(BigDecimal.ZERO);

  BigDecimal value;

  private Value(BigDecimal value) {
    this.value = value;
  }

  public static Value of(BigDecimal value) {
    if (value == null || value.signum() < 0) {
      value = BigDecimal.valueOf(0L, 2);
    }
    return new Value(value);
  }

  public BigDecimal asDecimal() {
    return this.value;
  }

  public Value multiply(BigDecimal multiplier) {
    return Value.of(this.value.multiply(multiplier, MathContext.DECIMAL32));
  }

  public Value multiply(Amount multiplier) {
    return Value.of(this.value.multiply(multiplier.asDecimal(), MathContext.DECIMAL32));
  }

  public Value divide(BigDecimal divider) {
    return Value.of(this.value.divide(divider, 2, RoundingMode.HALF_UP));
  }

  public Value add(Value toAdd) {
    return Value.of(this.value.add(toAdd.value));
  }

  public Value toNett(Vat vat) {
    BigDecimal multiplier = BigDecimal.ONE.subtract(vat.asDecimal());
    return this.multiply(multiplier);
  }
}
