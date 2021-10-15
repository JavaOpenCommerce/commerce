package com.example.javaopencommerce.order;

import io.smallrye.mutiny.Uni;
import java.util.List;

interface OrderRepository {

  Uni<List<OrderModel>> findOrderByUserId(Long id);

  Uni<OrderModel> findOrderById(Long id);

  Uni<OrderModel> saveOrder(OrderModel orderModel);

}
