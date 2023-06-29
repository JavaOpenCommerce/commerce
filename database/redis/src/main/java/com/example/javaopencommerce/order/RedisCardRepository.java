package com.example.javaopencommerce.order;

import com.example.javaopencommerce.catalog.CardItemEntity;
import java.util.List;


interface RedisCardRepository {

  List<CardItemEntity> getCardList(String id);

  List<CardItemEntity> saveCard(String id, List<CardItemEntity> products);

  void flushCard(String id);
}