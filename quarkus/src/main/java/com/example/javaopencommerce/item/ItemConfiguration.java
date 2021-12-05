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
  PsqlItemRepository psqlItemRepository(PgPool sqlClient) {
    return new PsqlItemRepositoryImpl(sqlClient);
  }

  @Produces
  @ApplicationScoped
  ItemRepository itemRepository(PsqlItemRepository repository) {
    return new ItemRepositoryImpl(repository);
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
  ItemQueryRepository itemQueryRepository(PsqlItemRepository repository,
      ItemDetailsLangResolver languageResolver, ItemDtoFactory factory) {
    return new ItemQueryRepositoryImpl(repository, languageResolver, factory);
  }

  @Produces
  @ApplicationScoped
  ItemService itemService(ItemRepository itemRepository) {
    return new ItemService(itemRepository);
  }

  @Produces
  @ApplicationScoped
  ItemFacade itemFacade(ItemService itemService) {
    return new ItemFacade(itemService);
  }

  @Produces
  @ApplicationScoped
  ItemQueryFacade itemQueryFacade(SearchService searchService, ItemQueryRepository repository) {
    return new ItemQueryFacade(searchService, repository);
  }

  @Produces
  @ApplicationScoped
  ItemController itemController(ItemQueryRepository queryRepository, ItemQueryFacade queryFacade) {
    return new ItemController(queryRepository, queryFacade);
  }
}
