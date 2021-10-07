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
  ItemDetailsLangResolver itemDetailsLangResolver(LanguageResolver languageResolver) {
    return new ItemDetailsLangResolver(languageResolver);
  }

  @Produces
  @ApplicationScoped
  ItemDtoFactory dtoFactory(ItemDetailsLangResolver languageResolver) {
    return new ItemDtoFactory(languageResolver);
  }

  @Produces
  @ApplicationScoped
  ItemQueryRepository itemQueryRepository(PgPool sqlClient, ItemDetailsLangResolver languageResolver) {
    return new ItemQueryRepositoryImpl(new PsqlItemRepositoryImpl(sqlClient), languageResolver);
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
