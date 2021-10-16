package com.example.javaopencommerce.category;

import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
class CategoryDetailsEntity {

  private Long id;
  private String name;
  private String description;
  private Locale lang;

  CategoryDetails toDetailsModel() {
    return CategoryDetails.builder()
        .id(id)
        .name(name)
        .description(description)
        .lang(lang)
        .build();
  }
}
