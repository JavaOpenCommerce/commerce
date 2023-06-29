package com.example.javaopencommerce.order.exceptions.ordervalidation;


import com.example.javaopencommerce.order.exceptions.ExceptionWithPayload;

public class OutOfStockItemsValidationException extends OrderValidationException implements
    ExceptionWithPayload {

  private final String outOfStockItems;

  public OutOfStockItemsValidationException(String outOfStockItems) {
    super("Not enough items in a stock!");
    this.outOfStockItems = outOfStockItems;
  }

  @Override
  public String getPayload() {
    return outOfStockItems;
  }
}
