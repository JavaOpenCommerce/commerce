package com.example.utils.converters;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import com.example.javaopencommerce.category.Category;
import com.example.javaopencommerce.category.CategoryDetailModel;
import com.example.javaopencommerce.category.CategoryDto;
import com.example.javaopencommerce.category.CategoryModel;
import java.util.List;
import java.util.stream.Collectors;

public interface CategoryConverter {

    static CategoryModel convertToModel(Category category) {

        List<CategoryDetailModel> details = ofNullable(category.getDetails()).orElse(emptyList())
                .stream()
                .map(CategoryDetailConverter::convertToModel)
                .collect(Collectors.toList());

        return CategoryModel.builder()
                .id(category.getId())
                .details(details)
                .build();
    }

    static CategoryModel convertDtoToModel(CategoryDto category) {
        return CategoryModel.builder()
                .id(category.getId())
                .details(emptyList()) //TODO
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
