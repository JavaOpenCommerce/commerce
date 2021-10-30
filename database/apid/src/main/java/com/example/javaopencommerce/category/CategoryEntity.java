package com.example.javaopencommerce.category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
class CategoryEntity {

  private Long id;
  private Long parentId;

  @Builder.Default
  private final List<CategoryDetailsEntity> details = new ArrayList<>();

  Category toCategoryModel() {
    return Category.builder()
        .id(id)
        .parentId(parentId)
        .details(details.stream()
            .map(CategoryDetailsEntity::toDetailsModel)
            .collect(Collectors.toUnmodifiableList()))
        .build();
  }
}
