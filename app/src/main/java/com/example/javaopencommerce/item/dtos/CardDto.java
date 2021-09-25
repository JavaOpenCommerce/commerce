package com.example.javaopencommerce.item.dtos;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CardDto {

    private List<ProductDto> products;
    private BigDecimal cardValueNett;
    private BigDecimal cardValueGross;
}
