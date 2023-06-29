package com.example.javaopencommerce.order.dtos;

import java.math.BigDecimal;

public record CardItemDto(ItemDto item, BigDecimal valueNett, BigDecimal valueGross, int amount) {

}