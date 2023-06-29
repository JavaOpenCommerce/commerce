package com.example.javaopencommerce.order.dtos;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderDto(UUID id, String status, String paymentStatus, List<OrderItemDto> items,
                       BigDecimal valueNett, BigDecimal valueGross, Instant creationDate) {

  public record OrderItemDto(Long itemId, String name, int amount, BigDecimal unitValueGross,
                             BigDecimal totalValueGross, BigDecimal vat) {

  }
}
