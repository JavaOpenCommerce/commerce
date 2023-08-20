package com.example.opencommerce.app.catalog;


import com.example.opencommerce.app.catalog.query.ItemQueryFacade;
import com.example.opencommerce.app.catalog.query.ItemQueryRepository;
import com.example.opencommerce.app.catalog.query.ItemSearchService;
import com.example.opencommerce.domain.catalog.CatalogRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

class CatalogConfiguration {

    @Produces
    @ApplicationScoped
    ItemQueryFacade itemQueryFacade(ItemSearchService searchService,
                                    ItemQueryRepository repository) {
        return new ItemQueryFacade(searchService, repository);
    }


    @Produces
    @ApplicationScoped
    CatalogFacade catalogFacade(CatalogRepository catalogRepository) {
        return new CatalogFacade(catalogRepository);
    }
}
