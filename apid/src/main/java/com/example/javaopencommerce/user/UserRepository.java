package com.example.javaopencommerce.user;

import io.smallrye.mutiny.Uni;
import java.util.List;

public interface UserRepository {

    Uni<List<UserEntity>> findByEmail(String query);

    Uni<UserEntity> findById(Long id);
}
