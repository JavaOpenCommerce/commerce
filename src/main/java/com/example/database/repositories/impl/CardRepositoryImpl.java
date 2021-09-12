package com.example.database.repositories.impl;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import com.example.javaopencommerce.order.CardProduct;
import com.example.javaopencommerce.order.CardRepository;
import com.example.utils.converters.JsonConverter;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.Json;
import io.vertx.mutiny.core.Promise;
import io.vertx.redis.client.RedisAPI;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ApplicationScoped
public class CardRepositoryImpl implements CardRepository {

    private final RedisAPI redisAPI;

    public CardRepositoryImpl(RedisAPI redisAPI) {
        this.redisAPI = redisAPI;
    }

    @Override
    public Uni<List<CardProduct>> getCardList(String id) {
        Promise<List<CardProduct>> promise = Promise.promise();
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
    public Uni<List<CardProduct>> saveCard(String id, List<CardProduct> products) {
        Promise<List<CardProduct>> promise = Promise.promise();
        this.redisAPI.set(List.of(id, Json.encode(products)), res -> {
            if (!res.succeeded()) {
                log.warn("Failed to store in redis, with message: {0}", res.cause());
            }
            log.info("Card successfully persisted in redis, status: " + res.result().toString());
            promise.complete(products);
        });
        return promise.future();
    }

    private List<CardProduct> jsonToPojo(String json) {
        return JsonConverter.convertToObject(json, ArrayList.class.getGenericSuperclass());
    }
}
