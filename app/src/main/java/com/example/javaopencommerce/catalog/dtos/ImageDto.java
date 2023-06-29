package com.example.javaopencommerce.catalog.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {

  private Long id;
  private String alt;
  private String url;
}
