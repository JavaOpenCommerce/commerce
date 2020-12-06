package com.example.rest.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {

    private Map<Long, ProductDto> products;
    private BigDecimal cardValueNett;
    private BigDecimal cardValueGross;
}
