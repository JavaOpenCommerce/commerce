package com.example.opencommerce.infra.catalog;

import com.example.opencommerce.app.catalog.query.ItemFamilyQueryRepository;
import com.example.opencommerce.app.catalog.query.ItemQueryFacade;
import com.example.opencommerce.app.pricing.query.PriceQueryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

class ItemConfiguration {

    @Produces
    @ApplicationScoped
    ItemViewMapper itemViewMapper() {
        return new ItemViewMapper();
    }

    @Produces
    @ApplicationScoped
    ItemSorter sortingUtil() {
        return new ItemSorter();
    }

    @Produces
    @ApplicationScoped
    ItemController itemController(ItemFamilyQueryRepository familyQueryRepository,
                                  ItemQueryFacade queryFacade,
                                  PriceQueryRepository priceQueryRepository,
                                  ItemViewMapper mapper,
                                  ItemSorter sorter) {
        return new ItemController(familyQueryRepository, queryFacade, priceQueryRepository, mapper, sorter);
    }
}
