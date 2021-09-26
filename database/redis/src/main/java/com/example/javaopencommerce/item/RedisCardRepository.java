package com.example.javaopencommerce.item;

import io.smallrye.mutiny.Uni;
import java.util.List;


interface RedisCardRepository {

  Uni<List<CardProductEntity>> getCardList(String id);

  Uni<List<CardProductEntity>> saveCard(String id, List<CardProductEntity> products);
}
