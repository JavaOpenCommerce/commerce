package com.example.utils.converters;


import com.example.business.models.CategoryDetailModel;
import com.example.business.models.CategoryModel;
import com.example.database.entity.CategoryDetails;

import java.util.Objects;

public interface CategoryDetailConverter {

    static CategoryDetailModel convertToModel(CategoryDetails details) {
        return CategoryDetailModel.builder()
                .id(details.getId())
                .name(details.getName())
                .description(details.getDescription())
                .lang(details.getLang())
                .build();
    }

    static CategoryDetailModel getCategoryDetailByLanguage(CategoryModel category, String lang, String defaultLang) {
        if (category.getDetails().isEmpty()) {
            return CategoryDetailModel.builder().name("Error").build();
        }

        return category.getDetails().stream()
                .filter(d -> Objects.nonNull(d.getLang().getLanguage()))
                .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(lang))
                .findFirst()
                .orElse(category.getDetails().stream()
                        .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(defaultLang))
                        .findFirst()
                        .orElse(CategoryDetailModel.builder().name("Error").build()));
    }
}
