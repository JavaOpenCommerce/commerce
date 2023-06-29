package com.example.javaopencommerce.catalog;

public class CatalogFacade {

  private final CatalogRepository repository;

  public CatalogFacade(CatalogRepository repository) {
    this.repository = repository;
  }

  CategoryEnricher getCategoryEnricher() {
    Category catalog = repository.getCatalog();
    return new CategoryEnricher(catalog);
  }
}
