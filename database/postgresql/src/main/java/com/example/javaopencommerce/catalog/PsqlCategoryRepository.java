package com.example.javaopencommerce.catalog;


interface PsqlCategoryRepository {

  String getCatalog();

  String saveCatalog(String catalog);
}
