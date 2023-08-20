package com.example.opencommerce.adapters.database.catalog.sql;

import com.example.opencommerce.domain.catalog.Category;

import java.util.List;
import java.util.UUID;

record CategoryEntity(UUID id, String name, String description,
                      List<CategoryEntity> children) {

    static CategoryEntity fromSnapshot(Category.CategorySnapshot snapshot) {
        List<CategoryEntity> children = snapshot.children()
                .stream()
                .map(CategoryEntity::fromSnapshot)
                .toList();
        return new CategoryEntity(snapshot.id()
                .id(), snapshot.name(), snapshot.description(),
                children);
    }

}
