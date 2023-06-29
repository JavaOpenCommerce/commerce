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
    String catalog = categoryRepository.getCatalog();
    try {
      CategoryEntity categoryEntity = objectMapper.readValue(catalog, CategoryEntity.class);
      return toModel(categoryEntity);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Problems with category tree recovery!", e);
    }
  }

  @Override
  public Category saveCatalog(Category catalog) {
    Category.CategorySnapshot catalogSnapshot = catalog.toSnapshot();
    CategoryEntity catalogEntity = CategoryEntity.fromSnapshot(catalogSnapshot);

    // TODO This needs refactor
    String catalogJson;
    try {
      catalogJson = objectMapper.writeValueAsString(catalogEntity);
      catalogJson = categoryRepository.saveCatalog(catalogJson);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Problems with category tree save/update", e);
    }

    try {
      CategoryEntity categoryEntity = objectMapper.readValue(catalogJson, CategoryEntity.class);
      return toModel(categoryEntity);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  private Category toModel(CategoryEntity entity) {
    Category catalog = Category.recover(entity.id(), entity.name(), entity.description());
    entity.children().stream().map(this::toModel).forEach(catalog::addChild);
    return catalog;
  }
}
