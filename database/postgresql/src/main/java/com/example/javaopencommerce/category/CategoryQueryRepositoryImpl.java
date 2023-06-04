package com.example.javaopencommerce.category;

import com.example.javaopencommerce.category.dtos.CategoryDto;
import java.util.List;

class CategoryQueryRepositoryImpl implements CategoryQueryRepository {

  private final PsqlCategoryRepository categoryRepository;
  private final CategoryDetailsLangResolver langResolver;

  CategoryQueryRepositoryImpl(
      PsqlCategoryRepository categoryRepository,
      CategoryDetailsLangResolver langResolver) {
    this.categoryRepository = categoryRepository;
    this.langResolver = langResolver;
  }

  @Override
  public List<CategoryDto> getAll() {
    return categoryRepository.getAll().map(this::getDtoList).await().indefinitely();
  }

  @Override
  public List<Long> getCategoryIdsByItemId(Long id) {
    return categoryRepository.getCategoryIdsForItem(id).await().indefinitely();
  }

  private List<CategoryDto> getDtoList(List<CategoryEntity> entities) {
    return entities.stream()
        .map(CategoryEntity::toCategoryModel)
        .map(this::toDto)
        .toList();
  }

  private CategoryDto toDto(Category category) {
    CategorySnapshot categorySnapshot = category.getSnapshot();
    CategoryDetailsSnapshot categoryDetailsSnapshot = langResolver
        .resolveDetails(categorySnapshot);

    return CategoryDto.builder()
        .id(categorySnapshot.getId())
        .parentId(categorySnapshot.getParentId())
        .categoryName(categoryDetailsSnapshot.getName())
        .description(categoryDetailsSnapshot.getDescription())
        .build();
  }
}
