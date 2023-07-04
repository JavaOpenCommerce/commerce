package com.example.javaopencommerce.order;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;

class OrderConfiguration {

    @Produces
    @ApplicationScoped
    OrderRepository orderRepository(EntityManager entityManager) {
        return new OrderRepositoryImpl(new PsqlOrderRepositoryImpl(entityManager));
    }

    @Produces
    @ApplicationScoped
    OrderQueryRepository orderQueryRepository(EntityManager entityManager) {
        return new OrderQueryRepositoryImpl(new PsqlOrderRepositoryImpl(entityManager));
    }

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
