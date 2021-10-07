package com.example.javaopencommerce.order;

import com.example.javaopencommerce.order.dtos.OrderDetailsDto;
import io.smallrye.mutiny.Uni;

public interface OrderQueryRepository {

  Uni<OrderDetailsDto> findOrderDetailsById(Long id);

}
