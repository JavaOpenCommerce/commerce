package com.example.javaopencommerce.item;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
class SearchItemDetails {

  private final String lang;
  private final String name;
  private final String description;
}
