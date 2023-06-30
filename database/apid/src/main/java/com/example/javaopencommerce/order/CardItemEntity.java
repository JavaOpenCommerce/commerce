package com.example.javaopencommerce.order;

import com.example.javaopencommerce.order.CardItem.CardItemSnapshot;

record CardItemEntity(Long itemId, String name, int amount) {

  static CardItemEntity fromSnapshot(CardItemSnapshot snapshot) {
    return new CardItemEntity(snapshot.id().id(), snapshot.itemSnapshot().name(),
        snapshot.amount().getValue());
  }
}
