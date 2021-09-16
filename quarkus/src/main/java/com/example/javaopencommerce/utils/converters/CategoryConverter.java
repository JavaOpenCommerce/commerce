package com.example.javaopencommerce.utils.converters;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import com.example.javaopencommerce.category.Category;
import com.example.javaopencommerce.category.CategoryDetails;
import com.example.javaopencommerce.category.CategoryDto;
import com.example.javaopencommerce.category.CategoryEntity;
import java.util.List;
import java.util.stream.Collectors;

public interface CategoryConverter {

    static Category convertToModel(CategoryEntity category) {

        List<CategoryDetails> details = ofNullable(category.getDetails()).orElse(emptyList())
                .stream()
                .map(CategoryDetailConverter::convertToModel)
                .collect(Collectors.toList());

        return Category.builder()
                .id(category.getId())
                .details(details)
                .build();
    }

    static Category convertDtoToModel(CategoryDto category) {
        return Category.builder()
                .id(category.getId())
                .details(emptyList()) //TODO
                .build();
    }

    static CategoryDto convertToDto(Category category, String lang, String defaultLang) {

        CategoryDetails details = CategoryDetailConverter.getCategoryDetailByLanguage(category, lang, defaultLang);

        return CategoryDto.builder()
                .id(category.getId())
                .categoryName(details.getName())
                .description(details.getDescription())
                .lang(details.getLang())
                .build();
    }
}
