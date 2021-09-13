package com.example.javaopencommerce.config;

import io.vertx.core.Vertx;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Slf4j
@ApplicationScoped
public class RedisClientConfig {

    @ConfigProperty(name = "com.example.redis.host")
    private String host;

    @ConfigProperty(name = "com.example.redis.port")
    private String port;
    @ConfigProperty(name = "com.example.redis.schema", defaultValue = "default")
    private String schema;

    @Produces
    public RedisAPI redisAPI() {

        Redis client = Redis
                .createClient(Vertx.vertx(), String.format("redis://%s:%s", this.host, this.port))
                .connect(result -> {
                    if (result.succeeded()) {
                        log.info("Successfully connected to Redis");
                    } else {
                        log.warn("Failed to connect to Redis with message: {}", result.cause());
                    }
                });
        return RedisAPI.api(client);
    }
}
