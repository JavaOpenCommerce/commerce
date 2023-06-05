package com.example.javaopencommerce.user;

import java.util.List;

public interface UserRepository {

  List<UserEntity> findByEmail(String query);

  UserEntity findById(Long id);
}
