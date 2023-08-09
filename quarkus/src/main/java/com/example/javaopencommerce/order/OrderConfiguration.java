package com.example.javaopencommerce.order;

import com.example.javaopencommerce.order.query.OrderQueryRepository;

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
    OrderController orderController(CreateOrderScenario createOrderScenario,
                                    OrderQueryRepository orderQueryRepository) {
        return new OrderController(createOrderScenario, orderQueryRepository);
    }
}
