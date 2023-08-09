package com.example.javaopencommerce.order.query;

import java.util.UUID;

public interface OrderQueryRepository {

    OrderDto findOrderById(UUID id);

}
