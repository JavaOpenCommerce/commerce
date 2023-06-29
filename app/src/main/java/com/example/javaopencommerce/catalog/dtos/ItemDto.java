package com.example.javaopencommerce.catalog.dtos;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ItemDto {

  Long id;
  String name;
  int stock;
  BigDecimal valueGross;
  ImageDto image;
  double vat;
}
