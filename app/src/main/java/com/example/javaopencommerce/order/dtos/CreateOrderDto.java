package com.example.javaopencommerce.order.dtos;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
public class CreateOrderDto {

    String paymentMethod;
    UUID addressId;
    UUID userId;
    CardDto card;
}
