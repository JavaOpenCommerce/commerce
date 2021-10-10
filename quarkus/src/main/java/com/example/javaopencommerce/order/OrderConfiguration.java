package com.example.javaopencommerce.order;

import com.example.javaopencommerce.item.ItemFacade;
import com.example.javaopencommerce.item.ItemQueryRepository;
import io.vertx.mutiny.pgclient.PgPool;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Singleton
class OrderConfiguration {

  @Produces
  @ApplicationScoped
  OrderRepository orderDetailsRepository(PgPool sqlClient) {
    return new OrderRepositoryImpl(new PsqlOrderRepositoryImpl(sqlClient));
  }

  @Produces
  @ApplicationScoped
  OrderDtoFactory orderDtoFactory(ItemQueryRepository itemQueryRepository) {
    return new OrderDtoFactory(itemQueryRepository);
  }

  @Produces
  @ApplicationScoped
  OrderQueryRepository orderQueryRepository(PgPool sqlClient, OrderDtoFactory orderDtoFactory) {
    return new OrderQueryRepositoryImpl(new PsqlOrderRepositoryImpl(sqlClient), orderDtoFactory);
  }

  @Produces
  @ApplicationScoped
  OrderDetailsService orderDetailsService(OrderRepository orderRepository) {
    return new OrderDetailsService(orderRepository);
  }

  @Produces
  @ApplicationScoped
  OrderFacade orderFacade(
      OrderDetailsService orderService,
      ItemQueryRepository itemQueryRepository,
      OrderDtoFactory orderDtoFactory) {

    return new OrderFacade(
        orderService,
        new OrderIntegrityValidator(itemQueryRepository),
        new OrderFactory(),
        orderDtoFactory
    );
  }

  @Produces
  @ApplicationScoped
  OrderController orderController(OrderFacade orderFacade,
      OrderQueryRepository orderQueryRepository) {
    return new OrderController(orderFacade, orderQueryRepository);
  }
}
