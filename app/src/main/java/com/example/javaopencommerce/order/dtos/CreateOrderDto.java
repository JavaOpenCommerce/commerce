package com.example.javaopencommerce.order.dtos;

import java.util.UUID;

public record CreateOrderDto(String paymentMethod, UUID addressId, UUID userId, CardDto card) {

}
