package com.example.opencommerce.app.order.query;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ItemDto(Long id, String name, int stock, BigDecimal valueGross, String imageUri, double vat) {

}
