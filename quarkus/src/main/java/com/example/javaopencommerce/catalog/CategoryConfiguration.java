package com.example.javaopencommerce.catalog;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

class CategoryConfiguration {

    @Produces
    @ApplicationScoped
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Produces
    @ApplicationScoped
    CatalogFacade catalogFacade(CatalogRepository catalogRepository) {
        return new CatalogFacade(catalogRepository);
    }
}
