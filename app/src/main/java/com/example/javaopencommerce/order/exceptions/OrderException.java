package com.example.javaopencommerce.order.exceptions;

public class OrderException extends RuntimeException {

  public OrderException() {
  }

  OrderException(String message, Throwable cause) {
    super(message, cause);
  }

  public OrderException(String message) {
    super(message);
  }
}
