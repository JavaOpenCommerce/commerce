package com.example.javaopencommerce.order;

import com.example.javaopencommerce.order.dtos.OrderDetailsDto;
import com.example.javaopencommerce.order.exceptions.OrderPersistenceException;
import io.smallrye.mutiny.Uni;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderFacade {

  private final OrderDetailsService orderDetailsService;
  private final OrderIntegrityValidator integrityValidator;
  private final OrderFactory orderFactory;
  private final OrderDtoFactory orderDtoFactory;

  public OrderFacade(OrderDetailsService orderDetailsService,
      OrderIntegrityValidator integrityValidator,
      OrderFactory orderFactory,
      OrderDtoFactory orderDtoFactory) {
    this.orderDetailsService = orderDetailsService;
    this.integrityValidator = integrityValidator;
    this.orderFactory = orderFactory;
    this.orderDtoFactory = orderDtoFactory;
  }

  public Uni<OrderDetailsDto> makeOrder(OrderDetailsDto orderDetailsDto) {
    integrityValidator.validateOrder(orderDetailsDto).subscribe();

    OrderDetails orderDetails = orderFactory.toOrderModel(orderDetailsDto);
    return orderDetailsService.saveOrderDetails(orderDetails)
        .onFailure()
        .transform(err -> {
          log.warn("Submitting and saving order failed: {}:\n {}", err.getMessage(),
              Arrays.toString(err.getStackTrace()));
          return new OrderPersistenceException(
              "Submitting and saving order failed!", err);
        }).flatMap(od -> {
          log.info("Order succesfully persisted, with id: {}", od.getSnapshot().getId());
          return orderDtoFactory.toDto(od);
        });
  }
}
