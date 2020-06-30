package com.example.utils.converters;

import com.example.business.models.CategoryDetailModel;
import com.example.business.models.CategoryModel;
import com.example.database.entity.Category;
import com.example.rest.dtos.CategoryDto;

import java.util.Set;
import java.util.stream.Collectors;

public interface CategoryConverter {

    static CategoryModel convertToModel(Category category) {

        Set<CategoryDetailModel> details = category.getDetails().stream()
                .map(d -> CategoryDetailConverter.convertToModel(d))
                .collect(Collectors.toSet());

        return CategoryModel.builder()
                .id(category.getId())
                .details(details)
                .build();
    }

    static CategoryDto convertToDto(CategoryModel category, String lang, String defaultLang) {

        CategoryDetailModel details = CategoryDetailConverter.getCategoryDetailByLanguage(category, lang, defaultLang);

        return CategoryDto.builder()
                .id(category.getId())
                .categoryName(details.getName())
                .description(details.getDescription())
                .lang(details.getLang())
                .build();
    }
}
