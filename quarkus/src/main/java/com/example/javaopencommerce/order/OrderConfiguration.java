package com.example.javaopencommerce.order;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

class OrderConfiguration {

    @Produces
    @ApplicationScoped
    OrderFacade orderFacade(CardRepository cardRepository, OrderRepository orderRepository) {
        return new OrderFacade(cardRepository, orderRepository);
    }

    @Produces
    @ApplicationScoped
    OrderController orderController(OrderFacade orderFacade,
                                    OrderQueryRepository orderQueryRepository) {
        return new OrderController(orderFacade, orderQueryRepository);
    }
}
