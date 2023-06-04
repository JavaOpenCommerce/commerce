package com.example.javaopencommerce.category.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CategoryDto {

  private Long id;
  private Long parentId;
  private String categoryName;
  private String description;
}
