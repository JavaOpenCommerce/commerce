package com.example.javaopencommerce.order;

import com.example.javaopencommerce.catalog.ItemQueryRepository;
import io.quarkus.redis.datasource.RedisDataSource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

class CardConfiguration {

  @Produces
  @ApplicationScoped
  ItemMapper itemMapper() {
    return new ItemMapper();
  }

  @Produces
  @ApplicationScoped
  CardRepository cardRepository(RedisDataSource redisClient, ItemQueryRepository itemRepository,
      ItemMapper mapper) {
    return new CardRepositoryImpl(new RedisCardRepositoryImpl(redisClient), itemRepository, mapper);
  }

  @Produces
  @ApplicationScoped
  CardFacade cardFacade(ItemQueryRepository itemRepository, CardRepository cardRepository,
      ItemMapper mapper) {
    return new CardFacade(itemRepository, cardRepository, mapper);
  }

  @Produces
  @ApplicationScoped
  CardController cardController(CardFacade cardFacade, ItemQueryRepository productQueryRepository) {
    return new CardController(cardFacade, productQueryRepository);
  }
}
