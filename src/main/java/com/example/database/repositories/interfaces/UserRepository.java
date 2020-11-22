package com.example.database.repositories.interfaces;

import com.example.database.entity.UserEntity;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface UserRepository {

    Uni<List<UserEntity>> findByEmail(String query);

    Uni<UserEntity> findById(Long id);
}
