package com.example.javaopencommerce.item.dtos;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

    private ItemDto item;
    private BigDecimal valueNett;
    private BigDecimal valueGross;
    private int amount;
}