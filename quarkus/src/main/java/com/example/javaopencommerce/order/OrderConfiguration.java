package com.example.javaopencommerce.order;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

class OrderConfiguration {

//    @Produces
//    @ApplicationScoped
//    OrderFacade orderFacade(CardRepository cardRepository,
//                            OrderRepository orderRepository,
//                            CardFactory cardFactory,
//                            ReserveItemScenario reserveItemScenario) {
//        return new OrderFacade(cardRepository, orderRepository, cardFactory, reserveItemScenario);
//    }

    @Produces
    @ApplicationScoped
    OrderController orderController(OrderFacade orderFacade,
                                    OrderQueryRepository orderQueryRepository) {
        return new OrderController(orderFacade, orderQueryRepository);
    }
}
