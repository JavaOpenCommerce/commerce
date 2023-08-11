package com.example.opencommerce.adapters.cache.card;

import com.example.opencommerce.domain.order.CardItem.CardItemSnapshot;

record CardItemEntity(Long itemId, int amount) {

    static CardItemEntity fromSnapshot(CardItemSnapshot snapshot) {
        return new CardItemEntity(snapshot.id()
                .asLong(), snapshot.amount()
                .getValue());
    }
}
