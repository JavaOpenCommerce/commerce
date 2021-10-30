package com.example.javaopencommerce.category;

import static java.util.stream.Collectors.toUnmodifiableList;

import io.smallrye.mutiny.Uni;
import java.util.List;

class CategoryRepositoryImpl implements CategoryRepository {

  private final PsqlCategoryRepository categoryRepository;

  CategoryRepositoryImpl(
      PsqlCategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public Uni<List<Category>> getAll() {
    return categoryRepository.getAll()
        .map(this::toModelList);
  }

  @Override
  public Uni<List<Category>> getCategoriesForItem(Long id) {
    return categoryRepository.getCategoriesForItem(id)
        .map(this::toModelList);
  }

  @Override
  public Uni<List<Category>> getCategoriesForItems(List<Long> ids) {
    return categoryRepository.getCategoriesForItems(ids)
        .map(this::toModelList);
  }

  private List<Category> toModelList(List<CategoryEntity> entities) {
    return entities.stream()
        .map(CategoryEntity::toCategoryModel)
        .collect(toUnmodifiableList());
  }
}
