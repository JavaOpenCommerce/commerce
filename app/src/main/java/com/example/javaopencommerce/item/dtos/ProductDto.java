package com.example.javaopencommerce.item.dtos;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

  private ItemDto item;
  private BigDecimal valueNett;
  private BigDecimal valueGross;
  private int amount;
}