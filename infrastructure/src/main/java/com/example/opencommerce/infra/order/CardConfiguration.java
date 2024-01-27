package com.example.opencommerce.infra.order;

import com.example.opencommerce.app.catalog.query.ItemQueryRepository;
import com.example.opencommerce.app.order.CardOperations;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

class CardConfiguration {

    @Produces
    @ApplicationScoped
    CardController cardController(CardOperations cardOperations, ItemQueryRepository productQueryRepository) {
        return new CardController(cardOperations, productQueryRepository);
    }
}
