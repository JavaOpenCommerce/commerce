package com.example.javaopencommerce.category;

import com.example.javaopencommerce.category.dtos.CategoryDto;
import java.util.List;

public interface CategoryQueryRepository {

  List<CategoryDto> getAll();

  List<Long> getCategoryIdsByItemId(Long id);

}
