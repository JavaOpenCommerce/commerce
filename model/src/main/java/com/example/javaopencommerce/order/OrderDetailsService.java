package com.example.javaopencommerce.order;

import io.smallrye.mutiny.Uni;


class OrderDetailsService {

    private final OrderRepository orderRepository;

    public OrderDetailsService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Uni<OrderDetails> getOrderDetailsById(Long id) {
        return this.orderRepository.findOrderDetailsById(id);
    }

    public Uni<OrderDetails> saveOrderDetails(OrderDetails orderDetailsModel) {
        return this.orderRepository.saveOrder(orderDetailsModel);
    }
}
