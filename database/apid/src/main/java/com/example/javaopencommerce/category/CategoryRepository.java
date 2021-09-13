package com.example.javaopencommerce.category;

import io.smallrye.mutiny.Uni;
import java.util.List;

public interface CategoryRepository {

    Uni<List<Category>> getAll();

    Uni<List<Category>> getCategoriesByItemId(Long id);

    Uni<List<Category>> getCategoriesListByIdList(List<Long> ids);
}
