package com.example.javaopencommerce.item.exceptions;

public class OutOfStockException extends RuntimeException {

  private ItemExceptionBody exception;

  public OutOfStockException(String message) {
    super(message);
  }

  public OutOfStockException(ItemExceptionBody itemExceptionBody) {
    super();
    exception = itemExceptionBody;
  }

  @Override
  public String getMessage() {
    return exception.getMessage();
  }
}
