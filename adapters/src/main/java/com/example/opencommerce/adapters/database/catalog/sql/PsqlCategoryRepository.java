package com.example.opencommerce.adapters.database.catalog.sql;


interface PsqlCategoryRepository {

    String getCatalog();

    String saveCatalog(String catalog);
}
