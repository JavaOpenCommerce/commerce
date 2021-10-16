package com.example.javaopencommerce.category;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
class CategorySnapshot {

  Long id;
  List<CategoryDetailsSnapshot> details;
}
