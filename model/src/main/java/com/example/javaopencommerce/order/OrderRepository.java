package com.example.javaopencommerce.order;

import java.util.List;

interface OrderRepository {

  List<OrderModel> findOrderByUserId(Long id);

  OrderModel findOrderById(Long id);

  OrderModel saveOrder(OrderModel orderModel);

}
