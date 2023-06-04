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
public class ItemDto {

  private Long id;
  private String name;
  private int stock;
  private BigDecimal valueGross;
  private Long imageId;
  private double vat;

}
