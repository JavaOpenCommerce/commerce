package com.example.javaopencommerce.order.exceptions.validation;

import static java.lang.String.format;

import java.math.BigDecimal;

public class OrderValueNotMatchingValidationException extends OrderValidationException {

  public OrderValueNotMatchingValidationException(BigDecimal orderValueGross,
      BigDecimal actualOrderValueGross) {
    super(format(
        "Issued order value not matching actual, real value: Issued order value: %s, Calculated value: %s",
        orderValueGross, actualOrderValueGross));
  }
}
