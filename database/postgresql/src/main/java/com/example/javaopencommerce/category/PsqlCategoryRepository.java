package com.example.javaopencommerce.category;

import io.smallrye.mutiny.Uni;
import java.util.List;

interface PsqlCategoryRepository {

  Uni<List<CategoryEntity>> getAll();

  Uni<List<CategoryEntity>> getCategoriesForItem(Long id);

  Uni<List<Long>> getCategoryIdsForItem(Long id);

  Uni<List<CategoryEntity>> getCategoriesForItems(List<Long> ids);
}
