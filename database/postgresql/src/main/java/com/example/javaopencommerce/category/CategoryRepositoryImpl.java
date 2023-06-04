package com.example.javaopencommerce.category;

import java.util.List;

class CategoryRepositoryImpl implements CategoryRepository {

  private final PsqlCategoryRepository categoryRepository;

  CategoryRepositoryImpl(
      PsqlCategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public List<Category> getAll() {
    return categoryRepository.getAll()
        .map(this::toModelList).await().indefinitely();
  }

  @Override
  public List<Category> getCategoriesForItem(Long id) {
    return categoryRepository.getCategoriesForItem(id)
        .map(this::toModelList).await().indefinitely();
  }

  @Override
  public List<Category> getCategoriesForItems(List<Long> ids) {
    return categoryRepository.getCategoriesForItems(ids)
        .map(this::toModelList).await().indefinitely();
  }

  private List<Category> toModelList(List<CategoryEntity> entities) {
    return entities.stream()
        .map(CategoryEntity::toCategoryModel)
        .toList();
  }
}
