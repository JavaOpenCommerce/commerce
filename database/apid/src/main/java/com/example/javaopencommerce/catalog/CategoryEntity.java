package com.example.javaopencommerce.catalog;

import java.util.List;
import java.util.UUID;

public record CategoryEntity(UUID id, String name, String description,
                             List<CategoryEntity> children) {

  static CategoryEntity fromSnapshot(Category.CategorySnapshot snapshot) {
    List<CategoryEntity> children = snapshot.children().stream().map(CategoryEntity::fromSnapshot)
        .toList();
    return new CategoryEntity(snapshot.id(), snapshot.name(), snapshot.description(), children);
  }

}
