package com.example.opencommerce.domain.catalog;

public interface CatalogRepository {

    Category getCatalog();

    Category saveCatalog(Category catalog);


}
