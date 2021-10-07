package com.example.javaopencommerce.order.exceptions;

import com.example.javaopencommerce.item.dtos.ProductDto;
import com.example.javaopencommerce.statics.JsonConverter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OutOfStockItemsValidationException extends OrderValidationException implements
    ExceptionWithPayload {

  private final List<ProductDto> outOfStockItems;

  public OutOfStockItemsValidationException(Collection<ProductDto> outOfStockItems) {
    super("Not enough items in a stock!");
    this.outOfStockItems = new ArrayList<>(outOfStockItems);
  }

  @Override
  public String getPayload() {
    return JsonConverter.convertToJson(outOfStockItems);
  }
}
