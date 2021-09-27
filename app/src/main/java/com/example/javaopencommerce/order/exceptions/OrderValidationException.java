package com.example.javaopencommerce.order.exceptions;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;

public class OrderValidationException extends RuntimeException {

  private List<OrderValidationException> derivativeExceptions = new ArrayList<>();

  private OrderValidationException() {}

  public OrderValidationException(String message) {
    super(format("OrderValidationException: %s", message));
  }

  public OrderValidationException(List<OrderValidationException> orderInaccuracies) {
    derivativeExceptions = orderInaccuracies;
  }

  public List<OrderValidationException> getDerivativeExceptions() {
    return new ArrayList<>(derivativeExceptions);
  }
}
