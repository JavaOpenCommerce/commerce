package com.example.javaopencommerce.category;

import com.example.javaopencommerce.category.dtos.CategoryDto;
import io.smallrye.mutiny.Uni;
import java.util.List;

public interface CategoryQueryRepository {

  Uni<List<CategoryDto>> getAll();

  Uni<List<CategoryDto>> getCategoriesByItemId(Long id);

  Uni<List<CategoryDto>> getCategoriesListByIdList(List<Long> ids);

}
