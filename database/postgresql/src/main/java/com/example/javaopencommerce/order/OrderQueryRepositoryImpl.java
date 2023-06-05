package com.example.javaopencommerce.order;

import com.example.javaopencommerce.order.dtos.OrderDto;

class OrderQueryRepositoryImpl implements OrderQueryRepository {

  private final PsqlOrderRepository psqlOrderRepository;
  private final OrderDtoFactory orderDtoFactory;

  OrderQueryRepositoryImpl(PsqlOrderRepository psqlOrderRepository,
      OrderDtoFactory orderDtoFactory) {
    this.psqlOrderRepository = psqlOrderRepository;
    this.orderDtoFactory = orderDtoFactory;
  }

  @Override
  public OrderDto findOrderById(Long id) {
    OrderModel order = psqlOrderRepository.findOrderById(id).map(OrderEntity::toOrderModel).await()
        .indefinitely();
    return orderDtoFactory.toDto(order);
  }
}
