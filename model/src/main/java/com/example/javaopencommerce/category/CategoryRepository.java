package com.example.javaopencommerce.category;

import io.smallrye.mutiny.Uni;
import java.util.List;

interface CategoryRepository {

  Uni<List<Category>> getAll();

  Uni<List<Category>> getCategoriesForItem(Long id);

  Uni<List<Category>> getCategoriesForItems(List<Long> ids);

}
