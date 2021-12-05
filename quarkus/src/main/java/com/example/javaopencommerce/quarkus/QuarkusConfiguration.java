package com.example.javaopencommerce.quarkus;

import com.example.javaopencommerce.producer.ProducerMapper;
import com.example.javaopencommerce.producer.ProducerRepository;
import com.example.javaopencommerce.producer.ProducerRepositoryImpl;
import com.example.javaopencommerce.user.UserRepository;
import com.example.javaopencommerce.user.UserRepositoryImpl;
import io.vertx.mutiny.pgclient.PgPool;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Singleton
public class QuarkusConfiguration {

    @Produces
    @ApplicationScoped
    ProducerRepository producerRepository(PgPool client) {
        return new ProducerRepositoryImpl(client);
    }

    @Produces
    @ApplicationScoped
    ProducerMapper producerMapper() {
        return new ProducerMapper();
    }


    @Produces
    @ApplicationScoped
    UserRepository userRepository(PgPool sqlClient) {
        return new UserRepositoryImpl(sqlClient);
    }
}
