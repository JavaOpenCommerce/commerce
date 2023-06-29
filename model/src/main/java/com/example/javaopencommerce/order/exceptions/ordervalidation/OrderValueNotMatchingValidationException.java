package com.example.javaopencommerce.order.exceptions.ordervalidation;

import static java.lang.String.format;

import com.example.javaopencommerce.Value;

public class OrderValueNotMatchingValidationException extends OrderValidationException {

  public OrderValueNotMatchingValidationException(Value actual,
      Value expected) {
    super(format(
        "Issued order value not matching actual, real value: Issued order value: %s, Calculated value: %s",
        actual, expected));
  }
}
