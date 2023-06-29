package com.example.javaopencommerce.order.dtos;

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
  String imageUri;
  double vat;
}
