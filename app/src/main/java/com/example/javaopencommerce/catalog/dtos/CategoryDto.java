package com.example.javaopencommerce.catalog.dtos;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

  private UUID id;
  private String name;
  private String description;
  private List<CategoryDto> children;
}
