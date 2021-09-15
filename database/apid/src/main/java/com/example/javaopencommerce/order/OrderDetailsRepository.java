package com.example.javaopencommerce.order;

import io.smallrye.mutiny.Uni;
import java.util.List;

public interface OrderDetailsRepository {

    Uni<List<OrderDetailsEntity>> findOrderDetailsByUserId(Long id);

    Uni<OrderDetailsEntity> findOrderDetailsById(Long id);

    Uni<OrderDetailsEntity> saveOrder(OrderDetailsEntity orderDetails);

}
