package com.example.opencommerce.app.order;

import com.example.opencommerce.app.order.query.OrderDto;
import com.example.opencommerce.domain.order.Order.OrderItem;
import com.example.opencommerce.domain.order.OrderSnapshot;

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

    private static OrderDto.OrderItemDto toDto(OrderItem orderItem) {
        return new OrderDto.OrderItemDto(orderItem.getId()
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
