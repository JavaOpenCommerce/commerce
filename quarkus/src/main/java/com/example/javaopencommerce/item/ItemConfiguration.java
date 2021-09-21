package com.example.javaopencommerce.item;

import com.example.javaopencommerce.LanguageResolver;
import io.vertx.mutiny.pgclient.PgPool;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Singleton
class ItemConfiguration {

  @Produces
  @ApplicationScoped
  ItemRepository itemRepository(PgPool sqlClient) {
    return new ItemRepositoryImpl(new PsqlItemRepositoryImpl(sqlClient));
  }

  @Produces
  @ApplicationScoped
  ItemService itemService(ItemRepository itemRepository) {
    return new ItemService(itemRepository);
  }

  @Produces
  @ApplicationScoped
  ItemFacade itemFacade(ItemService itemService, SearchService searchService,
      LanguageResolver languageResolver) {
    return new ItemFacade(
        itemService,
        searchService,
        new ItemDetailsLangResolver(languageResolver));
  }

  @Produces
  @ApplicationScoped
  ItemController itemController(ItemFacade itemFacade) {
    return new ItemController(itemFacade);
  }
}
