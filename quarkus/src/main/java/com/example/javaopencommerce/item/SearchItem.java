package com.example.javaopencommerce.item;

import static java.util.Collections.unmodifiableList;

import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
@EqualsAndHashCode
class SearchItem {

  private final Long id;
  private final double valueGross;
  private final Long imageId;
  private final Long producerId;
  private List<Long> categoryIds;
  private final List<SearchItemDetails> details;

  void setCategoryIds(List<Long> categoryIds) {
    this.categoryIds = unmodifiableList(categoryIds);
  }
}
