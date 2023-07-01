package com.example.javaopencommerce.order;

import java.util.Objects;

class ItemId {

  private final Long id;

  private ItemId(Long id) {
    this.id = id;
  }

  public static ItemId of(Long id) {
    return new ItemId(id);
  }

  Long id() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ItemId itemId = (ItemId) o;
    return Objects.equals(id, itemId.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
