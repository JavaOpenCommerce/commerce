package com.example.javaopencommerce.order;

import java.math.BigDecimal;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CardDto {

    private Map<Long, ProductDto> products;
    private BigDecimal cardValueNett;
    private BigDecimal cardValueGross;
}
