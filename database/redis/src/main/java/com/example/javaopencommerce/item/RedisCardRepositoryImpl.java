package com.example.javaopencommerce.item;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import com.example.javaopencommerce.statics.JsonConverter;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.Json;
import io.vertx.mutiny.core.Promise;
import io.vertx.redis.client.RedisAPI;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@Log4j2
class RedisCardRepositoryImpl implements RedisCardRepository {

  private final RedisAPI redisAPI;

  public RedisCardRepositoryImpl(RedisAPI redisAPI) {
    this.redisAPI = redisAPI;
  }

  @Override
  public Uni<List<CardProductEntity>> getCardList(String id) {
    Promise<List<CardProductEntity>> promise = Promise.promise();
    this.redisAPI.get(id, res -> {
      if (!res.succeeded()) {
        log.warn("Failed to get card, with message: {0}", res.cause());
      }
      promise.complete(ofNullable(res.result())
          .map(r -> jsonToPojo(r.toString()))
          .orElse(emptyList()));
    });
    return promise.future();
  }

  @Override
  public Uni<List<CardProductEntity>> saveCard(String id, List<CardProductEntity> products) {
    Promise<List<CardProductEntity>> promise = Promise.promise();
    this.redisAPI.set(List.of(id, Json.encode(products)), res -> {
      if (!res.succeeded()) {
        log.warn("Failed to store in redis, with message: {0}", res.cause());
      }
      log.info("Card successfully persisted in redis, status: {}", res.result().toString());
      promise.complete(products);
    });
    return promise.future();
  }

  private List<CardProductEntity> jsonToPojo(String json) {
    return JsonConverter.convertToObject(json, new ArrayList<CardProductEntity>(){}.getClass().getGenericSuperclass());
  }
}
