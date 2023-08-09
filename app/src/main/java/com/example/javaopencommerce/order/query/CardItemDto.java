package com.example.javaopencommerce.order.query;

import java.math.BigDecimal;

public record CardItemDto(ItemDto item, BigDecimal valueNett, BigDecimal valueGross, int amount,
                          String status) {

}