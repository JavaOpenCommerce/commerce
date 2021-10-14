package com.example.javaopencommerce.order;

import com.example.javaopencommerce.order.dtos.OrderDto;
import io.smallrye.mutiny.Uni;

public interface OrderQueryRepository {

  Uni<OrderDto> findOrderById(Long id);

}
