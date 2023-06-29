package com.example.javaopencommerce.order.exceptions;

import com.example.javaopencommerce.Amount;

public class OutOfStockException extends RuntimeException {

  private final static String ERROR_MSG_TEMPLATE = "Item out of stock. Current stock: %s pcs, asked for: %s pcs.";

  public OutOfStockException(String message) {
    super(message);
  }

  public OutOfStockException(Amount available, Amount needed) {
    super(String.format(ERROR_MSG_TEMPLATE, available, needed));
  }
}
