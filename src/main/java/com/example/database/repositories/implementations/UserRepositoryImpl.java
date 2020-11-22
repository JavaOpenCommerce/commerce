package com.example.database.repositories.implementations;

import com.example.database.entity.UserEntity;
import com.example.database.repositories.interfaces.UserRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

//TODO
@ApplicationScoped
public class UserRepositoryImpl implements UserRepository {

    private final PgPool client;

    public UserRepositoryImpl(PgPool client) {
        this.client = client;
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
