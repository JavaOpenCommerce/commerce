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
  ItemDtoFactory dtoFactory(LanguageResolver languageResolver) {
    return new ItemDtoFactory(new ItemDetailsLangResolver(languageResolver));
  }

  @Produces
  @ApplicationScoped
  ItemQueryRepository itemQueryRepository(PgPool sqlClient, ItemDetailsLangResolver resolver) {
    return new ItemQueryRepositoryImpl(new PsqlItemRepositoryImpl(sqlClient), resolver);
  }

  @Produces
  @ApplicationScoped
  ItemService itemService(ItemRepository itemRepository) {
    return new ItemService(itemRepository);
  }

  @Produces
  @ApplicationScoped
  ItemFacade itemFacade(ItemService itemService, SearchService searchService,
      ItemDtoFactory dtoFactory) {
    return new ItemFacade(
        itemService, searchService, dtoFactory);
  }

  @Produces
  @ApplicationScoped
  ItemController itemController(ItemFacade itemFacade) {
    return new ItemController(itemFacade);
  }
}
