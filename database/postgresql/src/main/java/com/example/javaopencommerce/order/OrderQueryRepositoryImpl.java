package com.example.javaopencommerce.order;

import com.example.javaopencommerce.order.dtos.OrderDetailsDto;
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
  public Uni<OrderDetailsDto> findOrderDetailsById(Long id) {
    return psqlOrderRepository.findOrderDetailsById(id)
        .map(OrderDetailsEntity::toOrderDetailsModel)
        .flatMap(orderDtoFactory::toDto);
  }
}
