package com.example.javaopencommerce.catalog;

import com.example.javaopencommerce.catalog.dtos.CategoryDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class CatalogQueryRepositoryImpl implements CatalogQueryRepository {

  private final PsqlCategoryRepository categoryRepository;
  private final ObjectMapper objectMapper;

  public CatalogQueryRepositoryImpl(PsqlCategoryRepository categoryRepository,
      ObjectMapper objectMapper) {
    this.categoryRepository = categoryRepository;
    this.objectMapper = objectMapper;
  }

  @Override
  public CategoryDto getCatalog() {
    String catalog = categoryRepository.getCatalog();
    try {
      return objectMapper.readValue(catalog, CategoryDto.class); // TODO go through entity!
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Problems while fetching catalog!", e);
    }
  }
}
