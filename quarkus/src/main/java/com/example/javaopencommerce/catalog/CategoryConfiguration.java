package com.example.javaopencommerce.catalog;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

@Singleton
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

  @Produces
  @ApplicationScoped
  PsqlCategoryRepository psqlCategoryRepository(EntityManager entityManager) {
    return new PsqlCategoryRepositoryImpl(entityManager);
  }

  @Produces
  @ApplicationScoped
  CatalogRepository categoryRepository(PsqlCategoryRepository psqlCategoryRepository,
      ObjectMapper objectMapper) {
    return new CatalogRepositoryImpl(psqlCategoryRepository, objectMapper);
  }

  @Produces
  @ApplicationScoped
  CatalogQueryRepository categoryQueryRepository(PsqlCategoryRepository psqlCategoryRepository,
      ObjectMapper objectMapper) {
    return new CatalogQueryRepositoryImpl(psqlCategoryRepository, objectMapper);
  }

}
