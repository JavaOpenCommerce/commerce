package com.example.opencommerce.app.order.query;

import java.util.UUID;

public interface OrderQueryRepository {

    OrderDto findOrderById(UUID id);

}
