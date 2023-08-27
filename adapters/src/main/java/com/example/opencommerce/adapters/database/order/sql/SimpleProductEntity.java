package com.example.opencommerce.adapters.database.order.sql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
