package com.example.javaopencommerce.catalog;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

import com.example.javaopencommerce.catalog.exceptions.CategoryNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

class Category {

  private final CategoryId id;
  private final String name;
  private final String description;
  private final Set<Category> children = new HashSet<>();
  private Category parent;

  private Category(CategoryId id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  static Category recover(UUID id, String title, String description) {
    requireNonNull(id);
    requireNonNull(title);
    requireNonNull(description);
    return new Category(CategoryId.of(id), title, description);
  }

  void addChild(Category categoryToAdd) {
    requireNonNull(categoryToAdd);
    categoryToAdd.setParent(this);
    children.add(categoryToAdd);
  }

  List<CategoryId> findAllSubcategoryIdsFor(CategoryId categoryId) {
    Optional<Category> categoryById = findCategoryById(categoryId);

    if (categoryById.isEmpty()) {
      throw new CategoryNotFoundException(categoryId);
    }

    List<CategoryId> ids = new ArrayList<>();
    Category temp = categoryById.get();

    while (temp.hasParent()) {
      ids.add(temp.id);
      temp = temp.parent;
    }
    ids.add(temp.id);

    return ids;
  }

  private void setParent(Category parent) {
    requireNonNull(parent);
    this.parent = parent;
  }

  private boolean hasParent() {
    return nonNull(this.parent);
  }

  private Optional<Category> findCategoryById(CategoryId categoryId) {
    if (this.id.equals(categoryId)) {
      return Optional.of(this);
    }
    for (Category cat : this.children) {
      Optional<Category> category = cat.findCategoryById(categoryId);
      if (category.isPresent()) {
        return category;
      }
    }
    return Optional.empty();
  }

  CategorySnapshot toSnapshot() {
    List<CategorySnapshot> children;
    if (this.children.isEmpty()) {
      children = Collections.emptyList();
    } else {
      children = this.children.stream().map(Category::toSnapshot).toList();
    }
    return new CategorySnapshot(this.id.id(), this.name, this.description, children);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Category category = (Category) o;
    return Objects.equals(id, category.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }


  record CategorySnapshot(UUID id, String name, String description,
                          List<CategorySnapshot> children) {

  }
}
