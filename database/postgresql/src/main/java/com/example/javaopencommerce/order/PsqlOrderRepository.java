package com.example.javaopencommerce.order;

import io.smallrye.mutiny.Uni;
import java.util.List;


interface PsqlOrderRepository {

    Uni<List<OrderEntity>> findOrderByUserId(Long id);

    Uni<OrderEntity> findOrderById(Long id);

    Uni<OrderEntity> saveOrder(OrderEntity order, List<SimpleProductEntity> products);

}
