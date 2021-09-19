package com.example.javaopencommerce.item;

import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

import com.example.javaopencommerce.LanguageResolver;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.util.List;

class ItemDetailsLangResolver {

  private final LanguageResolver languageResolver;

  ItemDetailsLangResolver(LanguageResolver languageResolver) {
    this.languageResolver = languageResolver;
  }


  ItemDetailsSnapshot resolveDetails(ItemSnapshot itemSnapshot) {
    List<ItemDetailsSnapshot> detailSnapshots = ofNullable(itemSnapshot.getDetails()).orElse(emptyList());
    return detailSnapshots.stream()
        .filter(d -> nonNull(d.getLang().getLanguage()))
        .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(languageResolver.getLanguage()))
        .findFirst()
        .orElse(detailSnapshots.stream()
            .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(languageResolver.getDefault()))
            .findFirst()
            .orElse(ItemDetailsSnapshot.builder()
                .name(HttpResponseStatus.NOT_FOUND.toString())
                .build()));
  }

}
