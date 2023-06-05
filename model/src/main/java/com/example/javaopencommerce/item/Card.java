package com.example.javaopencommerce.item;

import static com.example.javaopencommerce.statics.MessagesStore.BELOW_STOCK;
import static com.example.javaopencommerce.statics.MessagesStore.ITEM_404;
import static com.example.javaopencommerce.statics.MessagesStore.OK;
import static com.example.javaopencommerce.statics.MessagesStore.OUT_OF_STOCK;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.example.javaopencommerce.Value;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode
final class Card {

  private final Map<Long, Product> products;
  private Value cardValueNett = Value.of(ZERO);
  private Value cardValueGross = Value.of(ZERO);

  private Card(Map<Long, Product> productsMap) {
    this.products = new HashMap<>(productsMap);
    calculateCardValue();
  }

  static Card ofProducts(Map<Long, Product> productsMap) {
    return new Card(productsMap);
  }

  String increaseProductAmount(Item item) {
    ItemSnapshot itemSnapshot = item.getSnapshot();
    Product productModel = this.products.get(itemSnapshot.getId());

    //just adding to list with amount = 1 if does not exist yet
    if (isNull(productModel) && itemSnapshot.getStock() > 0) {
      this.products.put(itemSnapshot.getId(), Product.getProduct(item));
      return OK;
    }

    //Item does not exist in card, and stock is = 0
    if (isNull(productModel)) {
      return OUT_OF_STOCK;
    }

    int currentAmount = productModel.getAmount().asInteger();

    if (currentAmount + 1 <= itemSnapshot.getStock()) {
      productModel.setAmount(currentAmount + 1);
      return OK;
    }

    productModel.setAmount(itemSnapshot.getStock());
    return BELOW_STOCK;
  }

  String addProduct(Item item, int amount) {
    ItemSnapshot itemSnapshot = item.getSnapshot();
    if (itemSnapshot.getStock() < 1) {
      return OUT_OF_STOCK;
      //todo handling, issue #6
    }

    Long id = itemSnapshot.getId();
    if (!this.products.containsKey(id)) {
      Product product = Product.getProduct(item);
      this.products.put(id, product);
    }

    return updateProductAmount(id, amount, itemSnapshot.getStock());
  }

  private String updateProductAmount(Long productId, int amount, int stock) {
    Product product = this.products.get(productId);

    if (amount <= 0) {
      this.products.remove(productId);
      return OK;
    }
    if (amount <= stock) {
      product.setAmount(amount);
      return OK;
    }

    product.setAmount(stock);
    return BELOW_STOCK;

  }

  String removeProduct(Item item) {
    Long id = item.getSnapshot().getId();
    Product productModel = this.products.get(id);
    if (nonNull(productModel)) {
      this.products.remove(id);
      return OK;
    }
    return ITEM_404;
  }

  String decreaseProductAmount(Item item) {
    Long id = item.getSnapshot().getId();
    Product productModel = this.products.get(id);
    if (nonNull(productModel)) {
      int currentAmount = productModel.getAmount().asInteger();

      //remove entirely if amount would drop to zero
      if (currentAmount < 2) {
        this.products.remove(id);
      } else {
        productModel.setAmount(currentAmount - 1);
      }
      return OK;
    }
    return ITEM_404;
  }

  private void calculateCardValue() {
    this.cardValueGross = Value.of(this.products.values()
        .stream()
        .map(p -> p.getValueGross().asDecimal())
        .reduce(ZERO, BigDecimal::add));

    this.cardValueNett = Value.of(this.products.values()
        .stream()
        .map(p -> p.getValueNett().asDecimal())
        .reduce(ZERO, BigDecimal::add));
  }

  List<Product> getCardProducts() {
    return new ArrayList<>(products.values());
  }

  CardSnapshot getSnapshot() {
    calculateCardValue();
    return CardSnapshot.builder()
        .products(new ArrayList<>(
            products.values().stream()
                .map(Product::getSnapshot)
                .toList()))
        .cardValueGross(cardValueGross)
        .cardValueNett(cardValueNett)
        .build();
  }
}
