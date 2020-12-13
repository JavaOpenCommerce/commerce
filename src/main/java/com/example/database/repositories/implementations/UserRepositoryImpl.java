package com.example.database.repositories.implementations;

import com.example.database.entity.UserEntity;
import com.example.database.repositories.implementations.mappers.UserMapper;
import com.example.database.repositories.interfaces.UserRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

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
