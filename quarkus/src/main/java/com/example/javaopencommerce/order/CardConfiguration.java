package com.example.javaopencommerce.order;

import com.example.javaopencommerce.catalog.query.ItemQueryRepository;
import com.example.javaopencommerce.warehouse.query.WarehouseQueryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

class CardConfiguration {

    @Produces
    @ApplicationScoped
    ItemMapper itemMapper() {
        return new ItemMapper();
    }

    @Produces
    @ApplicationScoped
    CardFactory cardFactory(ItemQueryRepository itemRepository,
                            WarehouseQueryRepository warehouseRepository,
                            ItemMapper itemMapper) {
        return new CardFactory(itemRepository, warehouseRepository, itemMapper);
    }

    @Produces
    @ApplicationScoped
    CardOperations cardFacade(ItemQueryRepository itemRepository,
                              WarehouseQueryRepository warehouseRepository,
                              CardRepository cardRepository,
                              CardFactory cardFactory,
                              ItemMapper mapper) {
        return new CardOperations(cardFactory, itemRepository, warehouseRepository, cardRepository, mapper);
    }

    @Produces
    @ApplicationScoped
    CardController cardController(CardOperations cardOperations, ItemQueryRepository productQueryRepository) {
        return new CardController(cardOperations, productQueryRepository);
    }
}
