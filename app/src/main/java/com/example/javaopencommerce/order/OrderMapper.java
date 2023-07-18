package com.example.javaopencommerce.order;

import com.example.javaopencommerce.order.Order.OrderItem;
import com.example.javaopencommerce.order.dtos.OrderDto;
import com.example.javaopencommerce.order.dtos.OrderDto.OrderItemDto;

class OrderMapper {

    private OrderMapper() {
    }

    static OrderDto toDto(OrderSnapshot orderSnapshot) {
        return OrderDto.builder()
                .id(orderSnapshot.getId()
                        .asUUID())
                .status(orderSnapshot.getOrderStatus()
                        .name())
                .paymentStatus(orderSnapshot.getPaymentStatus()
                        .name())
                .valueNett(orderSnapshot.getOrderValueNett()
                        .asDecimal())
                .valueGross(orderSnapshot.getOrderValueGross()
                        .asDecimal())
                .creationDate(orderSnapshot.getCreationDate())
                .items(orderSnapshot.getOrderBody()
                        .stream()
                        .map(OrderMapper::toDto)
                        .toList())
                .build();
    }

    private static OrderItemDto toDto(OrderItem orderItem) {
        return new OrderItemDto(orderItem.getId()
                .asLong(), orderItem.getName(),
                orderItem.getAmount()
                        .getValue(), orderItem.getValueGross()
                .asDecimal(),
                orderItem.getValueGross()
                        .asDecimal()
                        .multiply(orderItem.getAmount()
                                .asDecimal()),
                orderItem.getVat()
                        .asDecimal());
    }


}
