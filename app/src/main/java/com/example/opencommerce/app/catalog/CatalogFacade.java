package com.example.opencommerce.app.catalog;

import com.example.opencommerce.domain.catalog.CatalogRepository;
import com.example.opencommerce.domain.catalog.Category;

public class CatalogFacade {

    private final CatalogRepository repository;

    public CatalogFacade(CatalogRepository repository) {
        this.repository = repository;
    }

    public CategoryEnricher getCategoryEnricher() {
        Category catalog = repository.getCatalog();
        return new CategoryEnricher(catalog);
    }
}
