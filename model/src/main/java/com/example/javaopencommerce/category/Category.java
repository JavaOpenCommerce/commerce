package com.example.javaopencommerce.category;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
class Category {

  Long id;
  Long parentId;
  List<CategoryDetails> details;

  CategorySnapshot getSnapshot() {
    return CategorySnapshot.builder()
        .id(id)
        .parentId(parentId)
        .details(details.stream()
            .map(CategoryDetails::getSnapshot)
            .collect(Collectors.toUnmodifiableList()))
        .build();
  }
}
