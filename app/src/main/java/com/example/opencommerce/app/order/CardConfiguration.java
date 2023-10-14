package com.example.opencommerce.app.order;

import com.example.opencommerce.app.catalog.query.ItemQueryRepository;
import com.example.opencommerce.app.pricing.query.PriceQueryRepository;
import com.example.opencommerce.app.warehouse.query.WarehouseQueryRepository;
import com.example.opencommerce.domain.order.CardRepository;

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
                            PriceQueryRepository priceRepository,
                            ItemMapper itemMapper) {
        return new CardFactory(itemRepository, warehouseRepository, priceRepository, itemMapper);
    }

    @Produces
    @ApplicationScoped
    CardOperations cardFacade(ItemQueryRepository itemRepository,
                              WarehouseQueryRepository warehouseRepository,
                              CardRepository cardRepository,
                              CardFactory cardFactory,
                              PriceQueryRepository priceRepository,
                              ItemMapper mapper) {
        return new CardOperations(cardFactory, itemRepository, warehouseRepository, cardRepository, priceRepository, mapper);
    }
}
