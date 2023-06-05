package com.example.javaopencommerce.item;

import io.quarkus.redis.datasource.RedisDataSource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Singleton
class CardConfiguration {

  @Produces
  @ApplicationScoped
  CardRepository cardRepository(RedisDataSource redisClient, ItemRepository itemRepository) {
    return new CardRepositoryImpl(new RedisCardRepositoryImpl(redisClient), itemRepository);
  }

  @Produces
  @ApplicationScoped
  CardService cardService(CardRepository cardRepository, ItemRepository itemRepository) {
    return new CardService(cardRepository, itemRepository);
  }

  @Produces
  @ApplicationScoped
  CardFacade cardFacade(CardService cardService, ItemDtoFactory dtoFactory) {
    return new CardFacade(cardService, dtoFactory);
  }

  @Produces
  @ApplicationScoped
  CardController cardController(CardFacade cardFacade, ItemQueryRepository itemQueryRepository) {
    return new CardController(cardFacade, itemQueryRepository);
  }
}
