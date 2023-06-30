package com.example.javaopencommerce.order;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.list.ListCommands;
import java.util.Collections;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@Log4j2
class RedisCardRepositoryImpl implements RedisCardRepository {

  private final ListCommands<String, CardItemEntity> commands;

  public RedisCardRepositoryImpl(RedisDataSource redisClient) {
    this.commands = redisClient.list(CardItemEntity.class);
  }

  @Override
  public List<CardItemEntity> getCardList(String key) {
    return getAll(key);
  }

  @Override
  public List<CardItemEntity> saveCard(String key, List<CardItemEntity> products) {
    if ((int) commands.llen(key) != 0) {
      pruneList(key);
    }
    if (products.isEmpty()) {
      return Collections.emptyList();
    }
    commands.lpush(key, products.toArray(new CardItemEntity[0]));
    return getAll(key);
  }

  @Override
  public void flushCard(String key) {
    pruneList(key);
  }

  private void pruneList(String key) {
    commands.ltrim(key, 1, 0);
  }

  private List<CardItemEntity> getAll(String key) {
    return commands.lrange(key, 0, -1);
  }
}
