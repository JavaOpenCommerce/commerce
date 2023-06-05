package com.example.javaopencommerce.order;


class OrderService {

  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public OrderModel getOrderById(Long id) {
    return this.orderRepository.findOrderById(id);
  }

  public OrderModel saveOrder(OrderModel orderModelModel) {
    return this.orderRepository.saveOrder(orderModelModel);
  }
}
