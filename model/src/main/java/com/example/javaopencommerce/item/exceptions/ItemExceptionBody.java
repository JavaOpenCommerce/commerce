package com.example.javaopencommerce.item.exceptions;

import lombok.Getter;

@Getter
public final class ItemExceptionBody {

  private final Long itemId;
  private final String message;

  private ItemExceptionBody(Long itemId, String message) {
    this.itemId = itemId;
    this.message = message;
  }

  public static ItemExceptionBody create(Long itemId, String message) {
    return new ItemExceptionBody(itemId, message);
  }
}
