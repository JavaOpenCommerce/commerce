package com.example.javaopencommerce.order;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class SimpleProductEntity {
  private Long itemId;
  private String name;
  private Integer amount;
  private BigDecimal valueGross;
  private Double vat;
}
