package com.example.javaopencommerce.order;

import com.example.javaopencommerce.order.Order.OrderItem;
import com.example.javaopencommerce.order.dtos.OrderDto;
import com.example.javaopencommerce.order.dtos.OrderDto.OrderItemDto;

class OrderMapper {

  private OrderMapper() {
  }

  static OrderDto toDto(OrderSnapshot orderSnapshot) {
    return OrderDto.builder().id(orderSnapshot.getId().id())
        .status(orderSnapshot.getOrderStatus().name())
        .paymentStatus(orderSnapshot.getPaymentStatus().name())
        .valueNett(orderSnapshot.getOrderValueNett().getValue())
        .valueGross(orderSnapshot.getOrderValueGross().getValue())
        .creationDate(orderSnapshot.getCreationDate())
        .items(orderSnapshot.getOrderBody().stream().map(OrderMapper::toDto).toList()).build();
  }

  private static OrderItemDto toDto(OrderItem orderItem) {
    return new OrderItemDto(orderItem.getItemId().id(), orderItem.getName(),
        orderItem.getAmount().getValue(), orderItem.getValueGross().getValue(),
        orderItem.getValueGross().getValue().multiply(orderItem.getAmount().asDecimal()),
        orderItem.getVat().asDecimal());
  }


}
