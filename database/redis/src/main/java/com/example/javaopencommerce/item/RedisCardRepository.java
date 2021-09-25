package com.example.javaopencommerce.item;

import io.smallrye.mutiny.Uni;
import java.util.List;


//Move to database package
interface RedisCardRepository {

  public Uni<List<CardProductEntity>> getCardList(String id);

  Uni<List<CardProductEntity>> saveCard(String id, List<CardProductEntity> products);
}
