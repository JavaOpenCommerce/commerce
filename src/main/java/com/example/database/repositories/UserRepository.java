package com.example.database.repositories;

import com.example.database.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public List<User> searchUserByEmail(String query) {
        return list("email LIKE ?1", "%" + query.trim() + "%");
    }


}
