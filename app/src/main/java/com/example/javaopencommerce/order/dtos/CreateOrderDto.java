package com.example.javaopencommerce.order.dtos;

import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CreateOrderDto {

  String paymentMethod;
  UUID addressId;
  UUID userId;
  CardDto card;
}
