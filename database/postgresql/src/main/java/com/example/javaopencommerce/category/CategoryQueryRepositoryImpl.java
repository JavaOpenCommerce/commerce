package com.example.javaopencommerce.category;

import static java.util.stream.Collectors.toUnmodifiableList;

import com.example.javaopencommerce.category.dtos.CategoryDto;
import io.smallrye.mutiny.Uni;
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
  public Uni<List<CategoryDto>> getAll() {
    return categoryRepository.getAll().map(this::getDtoList);
  }

  @Override
  public Uni<List<CategoryDto>> getCategoriesByItemId(Long id) {
    return categoryRepository.getCategoriesByItemId(id).map(this::getDtoList);
  }

  @Override
  public Uni<List<CategoryDto>> getCategoriesListByIdList(List<Long> ids) {
    return categoryRepository.getCategoriesListByIdList(ids).map(this::getDtoList);
  }

  private List<CategoryDto> getDtoList(List<CategoryEntity> entities) {
    return entities.stream()
        .map(CategoryEntity::toCategoryModel)
        .map(this::toDto)
        .collect(toUnmodifiableList());
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
