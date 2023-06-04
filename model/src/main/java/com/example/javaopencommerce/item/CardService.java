package com.example.javaopencommerce.item;

import static com.example.javaopencommerce.item.Item.restore;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


class CardService {

  private final ItemRepository itemRepository;
  private final CardRepository cardRepository;

  public CardService(CardRepository cardRepository, ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
    this.cardRepository = cardRepository;
  }

  public Card getCard(String id) {
    List<Product> products = this.cardRepository.getCardList(id);
    return Card.ofProducts(getCardProducts(products));
  }

  public String addProductWithAmount(Long productId, int amount, String cardId) {
    Card card = getCard(cardId);
    Item item = this.itemRepository.getItemById(productId);
    String result = card.addProduct(item, amount);
    this.cardRepository.saveCard(cardId, card.getCardProducts());
    return result;
  }

  public String increaseProductAmount(Long itemId, String cardId) {
    Card card = getCard(cardId);
    Item cardItem = this.itemRepository.getItemById(itemId);
    String result = card.increaseProductAmount(cardItem);
    this.cardRepository.saveCard(cardId, card.getCardProducts());
    return result;
  }

  public String decreaseProductAmount(Long itemId, String cardId) {
    Card card = getCard(cardId);
    Item cardItem = this.itemRepository.getItemById(itemId);
    String result = card.decreaseProductAmount(cardItem);
    this.cardRepository.saveCard(cardId, card.getCardProducts());
    return result;
  }

  public String removeProduct(Long itemId, String cardId) {
    Card card = getCard(cardId);
    Item cardItem = this.itemRepository.getItemById(itemId);
    String result = card.removeProduct(cardItem);
    this.cardRepository.saveCard(cardId, card.getCardProducts());
    return result;
  }

  public void flushCard(String id) {
    this.cardRepository.saveCard(id, emptyList());
  }

  private Map<Long, Product> getCardProducts(List<Product> products) {
    List<Long> ids = products.stream()
        .map(Product::getSnapshot)
        .map(ProductSnapshot::getItemId).collect(toList());

    List<Item> cardItems = this.itemRepository.getItemsByIdList(ids);

    Map<Long, Product> cardProducts = new HashMap<>();
    for (ItemSnapshot im : cardItems.stream().map(Item::getSnapshot).toList()) {

      int amount = products.stream()
          .filter(p -> p.getSnapshot().getItemId().equals(im.getId()))
          .findFirst()
          .map(product -> product.getAmount().asInteger())
          .orElse(1);

      cardProducts.put(im.getId(), Product.getProduct(restore(im), amount));
    }
    return cardProducts;
  }
}
