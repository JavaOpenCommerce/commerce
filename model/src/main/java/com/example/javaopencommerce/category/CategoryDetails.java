package com.example.javaopencommerce.category;

import java.util.Locale;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
class CategoryDetails {

  Long id;
  String name;
  String description;
  Locale lang;

  CategoryDetailsSnapshot getSnapshot() {
    return CategoryDetailsSnapshot.builder()
        .id(id)
        .name(name)
        .description(description)
        .lang(lang)
        .build();
  }

}
