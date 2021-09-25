package com.example.javaopencommerce.quarkus;

import com.example.javaopencommerce.address.AddressRepository;
import com.example.javaopencommerce.address.AddressRepositoryImpl;
import com.example.javaopencommerce.category.CategoryRepository;
import com.example.javaopencommerce.category.CategoryRepositoryImpl;
import com.example.javaopencommerce.order.OrderDetailsRepository;
import com.example.javaopencommerce.order.OrderDetailsRepositoryImpl;
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
    OrderDetailsRepository orderDetailsRepository(PgPool sqlClient) {
        return new OrderDetailsRepositoryImpl(sqlClient);
    }

    @Produces
    @ApplicationScoped
    AddressRepository addressRepository(PgPool sqlClient) {
        return new AddressRepositoryImpl(sqlClient);
    }

    @Produces
    @ApplicationScoped
    UserRepository userRepository(PgPool sqlClient) {
        return new UserRepositoryImpl(sqlClient);
    }

    @Produces
    @ApplicationScoped
    CategoryRepository categoryRepository(PgPool sqlClient) {
        return new CategoryRepositoryImpl(sqlClient);
    }
}
