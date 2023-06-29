package com.example.javaopencommerce.order;

import java.util.List;
import java.util.UUID;


interface PsqlOrderRepository {

  List<OrderEntity> findOrderByUserId(UUID id);

  OrderEntity findOrderById(UUID id);

  OrderEntity saveOrder(OrderEntity order);

}
