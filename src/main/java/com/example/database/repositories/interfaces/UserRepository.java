package com.example.database.repositories.interfaces;

import com.example.database.entity.UserEntity;

import java.util.List;

public interface UserRepository {

    List<UserEntity> searchUserByEmail(String query);
}
