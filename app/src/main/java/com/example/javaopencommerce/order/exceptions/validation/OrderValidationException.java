package com.example.javaopencommerce.order.exceptions.validation;

import static java.lang.String.format;

import com.example.javaopencommerce.order.exceptions.OrderException;
import java.util.ArrayList;
import java.util.List;

public class OrderValidationException extends OrderException {

  private List<OrderValidationException> derivativeExceptions = new ArrayList<>();

  private OrderValidationException() {
  }

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
