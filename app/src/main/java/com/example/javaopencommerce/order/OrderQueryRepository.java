package com.example.javaopencommerce.order;

import com.example.javaopencommerce.order.dtos.OrderDto;
import java.util.UUID;

public interface OrderQueryRepository {

  OrderDto findOrderById(UUID id);

}
