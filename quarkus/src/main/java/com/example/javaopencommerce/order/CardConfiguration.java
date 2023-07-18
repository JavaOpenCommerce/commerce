package com.example.javaopencommerce.order;

import com.example.javaopencommerce.catalog.ItemQueryRepository;
import com.example.javaopencommerce.warehouse.WarehouseQueryRepository;

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
    CardFacade cardFacade(ItemQueryRepository itemRepository,
                          WarehouseQueryRepository warehouseRepository,
                          CardRepository cardRepository,
                          CardFactory cardFactory,
                          ItemMapper mapper) {
        return new CardFacade(cardFactory, itemRepository, warehouseRepository, cardRepository, mapper);
    }

    @Produces
    @ApplicationScoped
    CardController cardController(CardFacade cardFacade, ItemQueryRepository productQueryRepository) {
        return new CardController(cardFacade, productQueryRepository);
    }
}
