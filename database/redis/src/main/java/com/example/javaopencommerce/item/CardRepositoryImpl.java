package com.example.javaopencommerce.item;

import static com.example.javaopencommerce.item.Product.getProduct;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@Log4j2
class CardRepositoryImpl implements CardRepository {

  private final RedisCardRepository redisCardRepository;
  private final ItemRepository itemRepository;

  public CardRepositoryImpl(RedisCardRepository redisCardRepository,
      ItemRepository itemRepository) {
    this.redisCardRepository = redisCardRepository;
    this.itemRepository = itemRepository;
  }


  @Override
  public List<Product> getCardList(String id) {
    return restoreProducts(redisCardRepository.getCardList(id));
  }

  @Override
  public List<Product> saveCard(String id, List<Product> products) {
    List<CardProductEntity> productEntities = products.stream().map(Product::getSnapshot)
        .map(CardProductEntity::fromSnapshot).toList();

    return restoreProducts(redisCardRepository.saveCard(id, productEntities));
  }

  private List<Product> restoreProducts(List<CardProductEntity> productEntities) {
    List<Long> itemIds = productEntities.stream().map(CardProductEntity::getItemId).toList();
    List<Item> items = itemRepository.getItemsByIdList(itemIds);

    List<Product> products = new ArrayList<>();
    for (CardProductEntity cardProduct : productEntities) {
      Item matchingItem = items.stream()
          .filter(item -> item.getSnapshot().getId().equals(cardProduct.getItemId())).findFirst()
          .orElseThrow();
      products.add(getProduct(matchingItem, cardProduct.getAmount()));
    }
    return products;
  }
}
