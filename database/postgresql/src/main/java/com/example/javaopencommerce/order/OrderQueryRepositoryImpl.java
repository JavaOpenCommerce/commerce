package com.example.javaopencommerce.order;

import com.example.javaopencommerce.order.dtos.OrderDto;
import io.smallrye.mutiny.Uni;

class OrderQueryRepositoryImpl implements OrderQueryRepository {

  private final PsqlOrderRepository psqlOrderRepository;
  private final OrderDtoFactory orderDtoFactory;

  OrderQueryRepositoryImpl(
      PsqlOrderRepository psqlOrderRepository,
      OrderDtoFactory orderDtoFactory) {
    this.psqlOrderRepository = psqlOrderRepository;
    this.orderDtoFactory = orderDtoFactory;
  }

  @Override
  public Uni<OrderDto> findOrderById(Long id) {
    return psqlOrderRepository.findOrderById(id)
        .map(OrderEntity::toOrderModel)
        .flatMap(orderDtoFactory::toDto);
  }
}
