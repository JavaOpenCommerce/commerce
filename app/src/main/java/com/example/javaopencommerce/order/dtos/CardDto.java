package com.example.javaopencommerce.order.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {

    private List<CardItemDto> products;
    private BigDecimal cardValueNett;
    private BigDecimal cardValueGross;
}
