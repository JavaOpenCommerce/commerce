package com.example.javaopencommerce.utils.converters;


import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static java.util.Objects.nonNull;

import com.example.javaopencommerce.category.CategoryDetails;
import com.example.javaopencommerce.category.CategoryDetailsEntity;
import com.example.javaopencommerce.category.Category;

public interface CategoryDetailConverter {

    static CategoryDetails convertToModel(CategoryDetailsEntity details) {
        return CategoryDetails.builder()
                .id(details.getId())
                .name(details.getName())
                .description(details.getDescription())
                .lang(details.getLang())
                .build();
    }

    static CategoryDetails getCategoryDetailByLanguage(Category category, String lang, String defaultLang) {
        if (category.getDetails().isEmpty()) {
            return CategoryDetails.builder().name(NOT_FOUND.toString()).build();
        }

        return category.getDetails().stream()
                .filter(d -> nonNull(d.getLang().getLanguage()))
                .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(lang))
                .findFirst()
                .orElse(category.getDetails().stream()
                        .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(defaultLang))
                        .findFirst()
                        .orElse(CategoryDetails.builder().name(NOT_FOUND.toString()).build()));
    }
}
