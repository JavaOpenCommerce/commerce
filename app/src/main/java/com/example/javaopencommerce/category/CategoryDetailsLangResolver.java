package com.example.javaopencommerce.category;

import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

import com.example.javaopencommerce.LanguageResolver;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.util.List;

class CategoryDetailsLangResolver {

  private final LanguageResolver languageResolver;

  CategoryDetailsLangResolver(LanguageResolver languageResolver) {
    this.languageResolver = languageResolver;
  }

  CategoryDetailsSnapshot resolveDetails(CategorySnapshot categorySnapshot) {
    return resolveDetails(ofNullable(categorySnapshot.getDetails()).orElse(emptyList()));
  }

  CategoryDetailsSnapshot resolveDetails(List<CategoryDetailsSnapshot> detailSnapshots) {
    return detailSnapshots.stream()
        .filter(d -> nonNull(d.getLang().getLanguage()))
        .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(languageResolver.getLanguage()))
        .findFirst()
        .orElse(detailSnapshots.stream()
            .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(languageResolver.getDefault()))
            .findFirst()
            .orElse(CategoryDetailsSnapshot.builder()
                .name(HttpResponseStatus.NOT_FOUND.toString())
                .build()));
  }
}
