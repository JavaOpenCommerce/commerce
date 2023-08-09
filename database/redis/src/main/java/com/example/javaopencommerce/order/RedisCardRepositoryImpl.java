package com.example.javaopencommerce.order;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.keys.KeyCommands;
import io.quarkus.redis.datasource.list.ListCommands;
import lombok.extern.log4j.Log4j2;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.List;

@Log4j2
@ApplicationScoped
class RedisCardRepositoryImpl implements RedisCardRepository {

    private final ListCommands<String, CardItemEntity> listCommands;
    private final KeyCommands<String> keyCommands;

    public RedisCardRepositoryImpl(RedisDataSource redisClient) {
        this.listCommands = redisClient.list(CardItemEntity.class);
        this.keyCommands = redisClient.key(String.class);
    }

    @Override
    public List<CardItemEntity> getCardList(String key) {
        return getAll(key);
    }

    @Override
    public List<CardItemEntity> saveCard(String key, List<CardItemEntity> products) {
        if ((int) listCommands.llen(key) != 0) {
            pruneList(key);
        }
        if (products.isEmpty()) {
            return Collections.emptyList();
        }
        listCommands.lpush(key, products.toArray(new CardItemEntity[0]));
        return getAll(key);
    }

    @Override
    public void removeCard(String key) {
        keyCommands.del(key);
    }

    private void pruneList(String key) {
        listCommands.ltrim(key, 1, 0);
    }

    private List<CardItemEntity> getAll(String key) {
        return listCommands.lrange(key, 0, -1);
    }
}
