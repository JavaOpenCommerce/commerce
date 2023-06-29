package com.example.javaopencommerce.order.dtos;

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

  private List<CardItemDto> products;
  private BigDecimal cardValueNett;
  private BigDecimal cardValueGross;
}
