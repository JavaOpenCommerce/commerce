package com.example.opencommerce.infra.order;

import com.example.javaopencommerce.catalog.query.ItemQueryRepository;
import com.example.javaopencommerce.order.CardOperations;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

class CardConfiguration {

    @Produces
    @ApplicationScoped
    CardController cardController(CardOperations cardOperations, ItemQueryRepository productQueryRepository) {
        return new CardController(cardOperations, productQueryRepository);
    }
}
