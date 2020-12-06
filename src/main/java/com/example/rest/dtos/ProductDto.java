package com.example.rest.dtos;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private ItemDto item;
    private BigDecimal valueNett;
    private BigDecimal valueGross;
    private int amount;
}
