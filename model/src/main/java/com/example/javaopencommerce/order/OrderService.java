package com.example.javaopencommerce.order;

import io.smallrye.mutiny.Uni;


class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Uni<OrderModel> getOrderById(Long id) {
        return this.orderRepository.findOrderById(id);
    }

    public Uni<OrderModel> saveOrder(OrderModel orderModelModel) {
        return this.orderRepository.saveOrder(orderModelModel);
    }
}
