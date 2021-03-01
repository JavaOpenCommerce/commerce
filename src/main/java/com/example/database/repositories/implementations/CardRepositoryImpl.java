package com.example.database.repositories.implementations;

import com.example.database.entity.CardProduct;
import com.example.database.repositories.interfaces.CardRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.Json;
import io.vertx.mutiny.core.Promise;
import io.vertx.redis.client.RedisAPI;
import lombok.extern.log4j.Log4j2;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

import static com.example.utils.converters.JsonConverter.convertToObject;
import static io.vertx.mutiny.core.Promise.promise;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Log4j2
@ApplicationScoped
public class CardRepositoryImpl implements CardRepository {

    private final RedisAPI redisAPI;

    public CardRepositoryImpl(RedisAPI redisAPI) {
        this.redisAPI = redisAPI;
    }

    @Override
    public Uni<List<CardProduct>> getCardList(String id) {
        Promise<List<CardProduct>> promise = promise();
        redisAPI.get(id, res -> {
            if (!res.succeeded()) {
                log.warn("Failed to get card, with message: {0}", res.cause());
            }
            promise.complete(ofNullable(res.result())
                            .map(r -> jsonToPojo(r.toString()))
                            .orElse(emptyList()));
        });
        return promise.future().onComplete();
    }

    @Override
    public Uni<List<CardProduct>> saveCard(String id, List<CardProduct> products) {
        Promise<List<CardProduct>> promise = promise();
        redisAPI.set(List.of(id, Json.encode(products)),res -> {
            if (!res.succeeded()) {
                log.warn("Failed to store in redis, with message: {0}", res.cause());
            }
            log.info("Card successfully persisted in redis, status: " + res.result().toString());
            promise.complete(products);
        });
        return promise.future().onComplete();
    }

    private List<CardProduct> jsonToPojo(String json) {
        return convertToObject(json, new ArrayList<CardProduct>(){}.getClass().getGenericSuperclass());
    }
}
