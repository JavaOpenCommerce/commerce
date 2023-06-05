package com.example.javaopencommerce.item;

import java.util.List;


interface RedisCardRepository {

  List<CardProductEntity> getCardList(String id);

  List<CardProductEntity> saveCard(String id, List<CardProductEntity> products);

  void flushCard(String id);
}
