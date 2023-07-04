package com.example.javaopencommerce.catalog;

import com.example.javaopencommerce.catalog.dtos.CategoryDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

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
            CategoryEntity categoryEntity = objectMapper.readValue(catalog, CategoryEntity.class);
            return toDto(categoryEntity);
        } catch (JsonProcessingException e) {
            // TODO dedicated exception
            throw new RuntimeException("Problems while fetching catalog!", e);
        }
    }

    private CategoryDto toDto(CategoryEntity categoryEntity) {
        List<CategoryDto> children = categoryEntity.children()
                .stream()
                .map(this::toDto)
                .toList();
        return new CategoryDto(categoryEntity.id(), categoryEntity.name(), categoryEntity.description(),
                children);
    }
}
