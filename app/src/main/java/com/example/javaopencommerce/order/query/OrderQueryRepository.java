package com.example.javaopencommerce.order.query;

import com.example.javaopencommerce.order.query.OrderDto;

import java.util.UUID;

public interface OrderQueryRepository {

    OrderDto findOrderById(UUID id);

}
