package com.example.javaopencommerce.catalog;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

class ItemConfiguration {

    @Produces
    @ApplicationScoped
    ItemDtoFactory dtoFactory() {
        return new ItemDtoFactory();
    }

    @Produces
    @ApplicationScoped
    ItemQueryFacade itemQueryFacade(SearchService searchService,
                                    ItemQueryRepository repository) {
        return new ItemQueryFacade(searchService, repository);
    }

    @Produces
    @ApplicationScoped
    ItemController itemController(ItemQueryRepository itemQueryRepository,
                                  ItemFamilyQueryRepository familyQueryRepository,
                                  ItemQueryFacade queryFacade) {
        return new ItemController(itemQueryRepository, familyQueryRepository, queryFacade);
    }
}
