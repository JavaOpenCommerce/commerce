package com.example.business.converters;

import com.example.business.models.CategoryModel;
import com.example.database.entity.Category;

public interface CategoryConverter {

    static CategoryModel convertToModel(Category category) {
        return CategoryModel.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .build();
    }
}
