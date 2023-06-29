package com.example.javaopencommerce.order;


interface OrderRepository {

  Order findOrderById(OrderId id);

  Order saveOrder(Order order);

}
