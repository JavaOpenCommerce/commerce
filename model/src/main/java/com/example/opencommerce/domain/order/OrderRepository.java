package com.example.opencommerce.domain.order;


import com.example.opencommerce.domain.OrderId;

public interface OrderRepository {

    Order findOrderById(OrderId id);

    Order saveOrder(Order order);

}
