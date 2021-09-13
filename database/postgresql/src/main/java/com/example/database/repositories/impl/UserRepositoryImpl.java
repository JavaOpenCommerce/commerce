package com.example.database.repositories.impl;

import com.example.database.repositories.impl.mappers.UserMapper;
import com.example.javaopencommerce.user.UserEntity;
import com.example.javaopencommerce.user.UserRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

//TODO
@ApplicationScoped
public class UserRepositoryImpl implements UserRepository {

    private final PgPool client;
    private final UserMapper userMapper;

    public UserRepositoryImpl(PgPool client, UserMapper userMapper) {
        this.client = client;
        this.userMapper = userMapper;
    }

    @Override
    public Uni<List<UserEntity>> findByEmail(String query) {
        return null;
    }

    @Override
    public Uni<UserEntity> findById(Long id) {
        return Uni.createFrom().item(UserEntity.builder().id(1L).build());
    }
}
