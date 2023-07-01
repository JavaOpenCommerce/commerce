package com.example.javaopencommerce.catalog;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class CatalogRepositoryImpl implements CatalogRepository {

  private final PsqlCategoryRepository categoryRepository;
  private final ObjectMapper objectMapper;

  CatalogRepositoryImpl(PsqlCategoryRepository categoryRepository, ObjectMapper objectMapper) {
    this.categoryRepository = categoryRepository;
    this.objectMapper = objectMapper;
  }

  @Override
  public Category getCatalog() {
    String catalogJson = categoryRepository.getCatalog();
    return recoverCategory(catalogJson);
  }

  @Override
  public Category saveCatalog(Category catalog) {
    Category.CategorySnapshot catalogSnapshot = catalog.toSnapshot();
    CategoryEntity catalogEntity = CategoryEntity.fromSnapshot(catalogSnapshot);

    String catalogJson;
    try {
      catalogJson = objectMapper.writeValueAsString(catalogEntity);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Problems with category tree save/update", e);
    }

    catalogJson = categoryRepository.saveCatalog(catalogJson);
    return recoverCategory(catalogJson);
  }

  private Category recoverCategory(String catalogJson) {
    CategoryEntity categoryEntity;
    try {
      categoryEntity = objectMapper.readValue(catalogJson, CategoryEntity.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Problems with category tree recovery!", e);
    }
    return toModel(categoryEntity);
  }

  private Category toModel(CategoryEntity entity) {
    Category catalog = Category.recover(entity.id(), entity.name(), entity.description());
    entity.children().stream().map(this::toModel).forEach(catalog::addChildToThisCategory);
    return catalog;
  }
}
