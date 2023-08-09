package com.example.javaopencommerce.order;


import com.example.javaopencommerce.OrderId;

public interface OrderRepository {

    Order findOrderById(OrderId id);

    Order saveOrder(Order order);

}
