package com.example.opencommerce.infra.catalog;

import com.example.javaopencommerce.catalog.query.ItemFamilyQueryRepository;
import com.example.javaopencommerce.catalog.query.ItemQueryFacade;
import com.example.javaopencommerce.catalog.query.ItemQueryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

class ItemConfiguration {

    @Produces
    @ApplicationScoped
    ItemController itemController(ItemQueryRepository itemQueryRepository,
                                  ItemFamilyQueryRepository familyQueryRepository,
                                  ItemQueryFacade queryFacade) {
        return new ItemController(itemQueryRepository, familyQueryRepository, queryFacade);
    }
}
