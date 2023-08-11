package com.example.opencommerce.infra.order;

import com.example.javaopencommerce.order.CreateOrderScenario;
import com.example.javaopencommerce.order.query.OrderQueryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

class OrderConfiguration {

    @Produces
    @ApplicationScoped
    OrderController orderController(CreateOrderScenario createOrderScenario,
                                    OrderQueryRepository orderQueryRepository) {
        return new OrderController(createOrderScenario, orderQueryRepository);
    }
}
