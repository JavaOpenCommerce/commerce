package com.example.javaopencommerce.item;

import static com.example.javaopencommerce.item.Product.getProduct;

import io.smallrye.mutiny.Uni;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;

@Log4j2
class CardRepositoryImpl implements CardRepository {

  private final RedisCardRepository redisCardRepository;
  private final ItemRepository itemRepository;

  public CardRepositoryImpl(
      RedisCardRepository redisCardRepository,
      ItemRepository itemRepository) {
    this.redisCardRepository = redisCardRepository;
    this.itemRepository = itemRepository;
  }


  @Override
  public Uni<List<Product>> getCardList(String id) {
    return restoreProducts(redisCardRepository.getCardList(id));
  }

  @Override
  public Uni<List<Product>> saveCard(String id, List<Product> products) {
    List<CardProductEntity> productEntities = products.stream()
        .map(Product::getSnapshot)
        .map(CardProductEntity::fromSnapshot)
        .collect(Collectors.toUnmodifiableList());

    return restoreProducts(redisCardRepository.saveCard(id, productEntities));
  }

  private Uni<List<Product>> restoreProducts(Uni<List<CardProductEntity>> entities) {

    return entities
        .flatMap(prods -> {
          List<Long> itemIds = prods.stream().map(CardProductEntity::getItemId)
              .collect(Collectors.toList());

          Uni<List<Item>> itemsList = itemRepository.getItemsByIdList(itemIds);

          return itemsList.map(items -> {
            List<Product> products = new ArrayList<>();
            for (CardProductEntity cardProduct : prods) {
              Item matchingItem = items.stream()
                  .filter(
                      item -> item.getSnapshot().getId().equals(cardProduct.getItemId()))
                  .findFirst()
                  .orElseThrow();
              products.add(getProduct(matchingItem, cardProduct.getAmount()));
            }
            return products;
          });
        });
  }
}
