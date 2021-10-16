package com.example.javaopencommerce.category;

import com.example.javaopencommerce.LanguageResolver;
import io.vertx.mutiny.pgclient.PgPool;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Singleton
class CategoryConfiguration {

  @Produces
  @ApplicationScoped
  PsqlCategoryRepository psqlCategoryRepository(PgPool sqlClient) {
    return new PsqlCategoryRepositoryImpl(sqlClient);
  }

  @Produces
  @ApplicationScoped
  CategoryRepository categoryRepository(PsqlCategoryRepository psqlCategoryRepository) {
    return new CategoryRepositoryImpl(psqlCategoryRepository);
  }

  @Produces
  @ApplicationScoped
  CategoryDetailsLangResolver categoryDetailsLangResolver(LanguageResolver languageResolver) {
    return new CategoryDetailsLangResolver(languageResolver);
  }

  @Produces
  @ApplicationScoped
  CategoryQueryRepository categoryQueryRepository(PsqlCategoryRepository psqlCategoryRepository,
      CategoryDetailsLangResolver resolver) {
    return new CategoryQueryRepositoryImpl(psqlCategoryRepository, resolver);
  }

}
