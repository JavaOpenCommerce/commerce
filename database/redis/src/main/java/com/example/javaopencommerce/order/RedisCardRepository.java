package com.example.javaopencommerce.order;

import java.util.List;


interface RedisCardRepository {

    List<CardItemEntity> getCardList(String id);

    List<CardItemEntity> saveCard(String id, List<CardItemEntity> products);

    void removeCard(String id);
}
