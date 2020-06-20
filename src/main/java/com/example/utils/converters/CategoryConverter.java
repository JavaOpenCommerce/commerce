package com.example.utils.converters;

import com.example.business.models.CategoryModel;
import com.example.database.entity.Category;
import com.example.rest.dtos.CategoryDto;

public interface CategoryConverter {

    static CategoryModel convertToModel(Category category) {
        return CategoryModel.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .build();
    }

    static CategoryDto convertToDto(CategoryModel category) {
        return CategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .build();
    }
}
