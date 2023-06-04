package com.example.javaopencommerce.order;

import com.example.javaopencommerce.order.dtos.OrderDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderFacade {

  private final OrderService orderService;
  private final OrderIntegrityValidator integrityValidator;
  private final OrderFactory orderFactory;
  private final OrderDtoFactory orderDtoFactory;

  public OrderFacade(OrderService orderService,
      OrderIntegrityValidator integrityValidator,
      OrderFactory orderFactory,
      OrderDtoFactory orderDtoFactory) {
    this.orderService = orderService;
    this.integrityValidator = integrityValidator;
    this.orderFactory = orderFactory;
    this.orderDtoFactory = orderDtoFactory;
  }

  public OrderDto makeOrder(OrderDto orderDto) {
    integrityValidator.validateOrder(orderDto);
    OrderModel orderModel = orderFactory.toOrderModel(orderDto);
    orderModel = orderService.saveOrder(orderModel);
    // TODO error handling
    return orderDtoFactory.toDto(orderModel);
  }
}
