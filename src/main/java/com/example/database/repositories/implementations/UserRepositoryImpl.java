package com.example.database.repositories.implementations;

import com.example.database.entity.UserEntity;
import com.example.database.repositories.interfaces.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class UserRepositoryImpl implements UserRepository {

    @Override
    public List<UserEntity> searchUserByEmail(String query) {
        //todo
        return null;
    }
}
