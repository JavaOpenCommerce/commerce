package com.example.database.dao;

import com.example.database.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDAO {
    Optional<Category> getById(Long id);

    List<Category> getAll();

    Category save(Category category);

    void delete(Category category);

    void deleteById(Long id);
}
