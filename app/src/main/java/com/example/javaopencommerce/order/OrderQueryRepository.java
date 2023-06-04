package com.example.javaopencommerce.order;

import com.example.javaopencommerce.order.dtos.OrderDto;

public interface OrderQueryRepository {

  OrderDto findOrderById(Long id);

}
