package com.example.opencommerce.adapters.database.order.sql;

import com.example.opencommerce.app.order.query.OrderDto;
import com.example.opencommerce.app.order.query.OrderDto.OrderItemDto;
import com.example.opencommerce.statics.JsonConverter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class QueryOrderMapper {

    private QueryOrderMapper() {
    }

    static OrderDto toQuery(OrderEntity entity) {
        List<SimpleProductEntity> items = JsonConverter.convertToObject(entity.getSimpleProductsJson(),
                new ArrayList<SimpleProductEntity>() {
                }.getClass()
                        .getGenericSuperclass());

        return OrderDto.builder()
                .id(entity.getId())
                .status(entity.getStatus())
                .paymentStatus(entity.getPaymentStatus())
                .valueNett(entity.getValueNett())
                .valueGross(entity.getValueGross())
                .creationDate(entity.getCreatedAt())
                .items(items.stream()
                        .map(QueryOrderMapper::toDto)
                        .toList())
                .build();
    }

    private static OrderItemDto toDto(SimpleProductEntity orderItem) {
        return new OrderItemDto(orderItem.getItemId(), orderItem.getName(), orderItem.getAmount(),
                orderItem.getValueGross(),
                orderItem.getValueGross()
                        .multiply(BigDecimal.valueOf(orderItem.getAmount())),
                BigDecimal.valueOf(orderItem.getVat()));
    }
}