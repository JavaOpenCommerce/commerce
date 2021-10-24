package com.example.javaopencommerce.item;

import static com.example.javaopencommerce.item.Item.restore;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import io.smallrye.mutiny.Uni;
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

    public Uni<Card> getCard(String id) {
        return this.cardRepository.getCardList(id).flatMap(products ->
                getCardProducts(products).map(Card::getInstance));
    }

    public Uni<String> addProductWithAmount(Long productId, int amount, String id) {
        return Uni.combine().all().unis(getCard(id), this.itemRepository.getItemById(productId))
                .combinedWith((card, cardItem) -> {
                    String result = card.addProduct(cardItem, amount);
                    this.cardRepository.saveCard(id, card.getCardProducts()).subscribe();
                    return result;
                });
    }

    public Uni<String> increaseProductAmount(Long itemId, String id) {
        return Uni.combine().all().unis(getCard(id), this.itemRepository.getItemById(itemId))
                .combinedWith((card, cardItem) -> {
                    String result = card.increaseProductAmount(cardItem);
                    this.cardRepository.saveCard(id, card.getCardProducts());
                    return result;
                });
    }

    public Uni<String> decreaseProductAmount(Long itemId, String id) {
        return Uni.combine().all().unis(getCard(id), this.itemRepository.getItemById(itemId))
                .combinedWith((card, cardItem) -> {
                    String result = card.decreaseProductAmount(cardItem);
                    this.cardRepository.saveCard(id, card.getCardProducts());
                    return result;
                });
    }

    public Uni<String> removeProduct(Long itemId, String id) {
        return Uni.combine().all().unis(getCard(id), this.itemRepository.getItemById(itemId))
                .combinedWith((card, cardItem) -> {
                    String result = card.removeProduct(cardItem);
                    this.cardRepository.saveCard(id, card.getCardProducts());
                    return result;
                });
    }

    public void flushCard(String id) {
        this.cardRepository.saveCard(id, emptyList());
    }

    private Uni<Map<Long, Product>> getCardProducts(List<Product> products) {
        List<Long> ids = products.stream()
            .map(Product::getSnapshot)
            .map(ProductSnapshot::getItemId).collect(toList());

        return this.itemRepository.getItemsByIdList(ids)
            .map(items -> {
              Map<Long, Product> cardProducts = new HashMap<>();
              for (ItemSnapshot im : items.stream().map(Item::getSnapshot).collect(toList())) {

                  int amount = products.stream()
                      .filter(p -> p.getSnapshot().getItemId().equals(im.getId()))
                      .findFirst()
                      .map(product -> product.getAmount().asInteger())
                      .orElse(1);

                  cardProducts.put(im.getId(), Product.getProduct(restore(im), amount));
              }
              return cardProducts;
            });
    }
}
