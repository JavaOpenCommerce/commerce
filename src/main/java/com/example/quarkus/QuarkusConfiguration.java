package com.example.quarkus;

import com.example.database.repositories.impl.AddressRepositoryImpl;
import com.example.database.repositories.impl.CategoryRepositoryImpl;
import com.example.database.repositories.impl.ImageRepositoryImpl;
import com.example.database.repositories.impl.ItemRepositoryImpl;
import com.example.database.repositories.impl.OrderDetailsRepositoryImpl;
import com.example.database.repositories.impl.ProducerRepositoryImpl;
import com.example.database.repositories.impl.UserRepositoryImpl;
import com.example.database.repositories.impl.mappers.AddressMapper;
import com.example.database.repositories.impl.mappers.CategoryMapper;
import com.example.database.repositories.impl.mappers.ItemMapper;
import com.example.database.repositories.impl.mappers.OrderDetailsMapper;
import com.example.database.repositories.impl.mappers.ProducerMapper;
import com.example.database.repositories.impl.mappers.UserMapper;
import com.example.javaopencommerce.address.AddressRepository;
import com.example.javaopencommerce.category.CategoryRepository;
import com.example.javaopencommerce.image.ImageRepository;
import com.example.javaopencommerce.item.ItemRepository;
import com.example.javaopencommerce.order.OrderDetailsRepository;
import com.example.javaopencommerce.producer.ProducerRepository;
import com.example.javaopencommerce.user.UserRepository;
import io.vertx.mutiny.pgclient.PgPool;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Singleton
public class QuarkusConfiguration {

    @Produces
    @ApplicationScoped
    ProducerRepository producerRepository(PgPool client, ProducerMapper producerMapper) {
        return new ProducerRepositoryImpl(client, producerMapper);
    }

    @Produces
    @ApplicationScoped
    ProducerMapper producerMapper() {
        return new ProducerMapper();
    }

    @Produces
    @ApplicationScoped
    ItemRepository itemRepository(PgPool sqlClient, ItemMapper itemMapper) {
        return new ItemRepositoryImpl(sqlClient, itemMapper);
    }

    @Produces
    @ApplicationScoped
    ItemMapper itemMapper() {
        return new ItemMapper();
    }

    @Produces
    @ApplicationScoped
    OrderDetailsRepository orderDetailsRepository(PgPool sqlClient, OrderDetailsMapper mapper) {
        return new OrderDetailsRepositoryImpl(sqlClient, mapper);
    }

    @Produces
    @ApplicationScoped
    OrderDetailsMapper orderDetailsMapper() {
        return new OrderDetailsMapper();
    }

    @Produces
    @ApplicationScoped
    AddressRepository addressRepository(PgPool sqlClient, AddressMapper mapper) {
        return new AddressRepositoryImpl(sqlClient, mapper);
    }

    @Produces
    @ApplicationScoped
    AddressMapper addressMapper() {
        return new AddressMapper();
    }

    @Produces
    @ApplicationScoped
    UserRepository userRepository(PgPool sqlClient, UserMapper mapper) {
        return new UserRepositoryImpl(sqlClient, mapper);
    }

    @Produces
    @ApplicationScoped
    UserMapper userMapper() {
        return new UserMapper();
    }

    @Produces
    @ApplicationScoped
    CategoryRepository categoryRepository(PgPool sqlClient, CategoryMapper mapper) {
        return new CategoryRepositoryImpl(sqlClient, mapper);
    }

    @Produces
    @ApplicationScoped
    CategoryMapper categoryMapper() {
        return new CategoryMapper();
    }

    @Produces
    @ApplicationScoped
    ImageRepository imageRepository() {
        return new ImageRepositoryImpl();
    }
}
