package com.example.javaopencommerce.item.dtos;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {

  private List<ProductDto> products;
  private BigDecimal cardValueNett;
  private BigDecimal cardValueGross;
}
