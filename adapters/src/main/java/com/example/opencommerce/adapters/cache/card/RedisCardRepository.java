package com.example.opencommerce.adapters.cache.card;

import java.util.List;


interface RedisCardRepository {

    List<CardItemEntity> getCardList(String id);

    List<CardItemEntity> saveCard(String id, List<CardItemEntity> products);

    void removeCard(String id);
}
