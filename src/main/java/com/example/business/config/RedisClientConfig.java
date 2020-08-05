package com.example.business.config;

import io.vertx.core.Vertx;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@JBossLog
@ApplicationScoped
public class RedisClientConfig {

    @ConfigProperty(name = "com.example.redis-host")
    private String HOST;

    @ConfigProperty(name = "com.example.redis-port")
    private int PORT;

    @Produces
    public RedisAPI redisAPI() {
        Redis client = Redis
                .createClient(Vertx.vertx(), SocketAddress.inetSocketAddress(PORT, HOST))
                .connect(result -> {
                    if (result.succeeded()) {
                        log.info("Successfully connected to Redis");
                    } else {
                        log.warnf("Failed to connect to Redis with message: {}", result.cause());
                    }
                });
        return RedisAPI.api(client);
    }
}
