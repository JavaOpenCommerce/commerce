package com.example.rest.dtos;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Builder
@EqualsAndHashCode
public class CardDto {

    private final Map<Long, ProductDto> products;
    private BigDecimal cardValueNett;
    private BigDecimal cardValueGross;
}
