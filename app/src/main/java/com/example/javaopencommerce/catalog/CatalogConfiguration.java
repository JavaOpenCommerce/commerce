package com.example.javaopencommerce.catalog;


import com.example.javaopencommerce.catalog.query.ItemQueryFacade;
import com.example.javaopencommerce.catalog.query.ItemQueryRepository;
import com.example.javaopencommerce.catalog.query.ItemSearchService;

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
