package com.example.utils.converters;


import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static java.util.Objects.nonNull;

import com.example.javaopencommerce.category.CategoryDetailModel;
import com.example.javaopencommerce.category.CategoryDetails;
import com.example.javaopencommerce.category.CategoryModel;

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
            return CategoryDetailModel.builder().name(NOT_FOUND.toString()).build();
        }

        return category.getDetails().stream()
                .filter(d -> nonNull(d.getLang().getLanguage()))
                .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(lang))
                .findFirst()
                .orElse(category.getDetails().stream()
                        .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(defaultLang))
                        .findFirst()
                        .orElse(CategoryDetailModel.builder().name(NOT_FOUND.toString()).build()));
    }
}
