package com.example.javaopencommerce.category;

import io.smallrye.mutiny.Uni;
import java.util.List;

public interface CategoryRepository {

    Uni<List<CategoryEntity>> getAll();

    Uni<List<CategoryEntity>> getCategoriesByItemId(Long id);

    Uni<List<CategoryEntity>> getCategoriesListByIdList(List<Long> ids);
}
