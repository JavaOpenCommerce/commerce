package com.example.javaopencommerce.order;

import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.item.Item;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.example.javaopencommerce.statics.MessagesStore.*;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Getter
@EqualsAndHashCode
public final class Card {

    private final Map<Long, Product> products;
    private Value cardValueNett = Value.of(ZERO);
    private Value cardValueGross = Value.of(ZERO);

    private Card(Map<Long, Product> productsMap) {
        this.products = new HashMap<>(productsMap);
        calculateCardValue();
    }

    public static Card getInstance(Map<Long, Product> productsMap) {
        return new Card(productsMap);
    }

    public String increaseProductAmount(Item item) {
        Product productModel = this.products.get(item.getId());

        //just adding to list with amount = 1 if does not exist yet
        if (isNull(productModel) && item.getStock() > 0) {
            this.products.put(item.getId(), Product.getProduct(item));
            return OK;
        }

        //Item does not exist in card, and stock is = 0
        if (isNull(productModel)) {
            return OUT_OF_STOCK;
        }

        int currentAmount = productModel.getAmount().asInteger();

        if (currentAmount + 1 <= item.getStock()) {
            productModel.setAmount(currentAmount + 1);
            return OK;
        }

        productModel.setAmount(item.getStock());
        return BELOW_STOCK;
    }

    public String addProduct(Item item, int amount) {
        if (item.getStock() < 1) {
            return OUT_OF_STOCK;
            //todo handling, issue #6
        }

        Long id = item.getId();
        if (!this.products.containsKey(id)) {
            Product product = Product.getProduct(item);
            this.products.put(id, product);
        }

        return updateProductAmount(id, amount, item.getStock());
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

    public String removeProduct(Item item) {
        Product productModel = this.products.get(item.getId());
        if (nonNull(productModel)) {
            this.products.remove(item.getId());
            return OK;
        }
        return ITEM_404;
    }

    public String decreaseProductAmount(Item item) {
        Product productModel = this.products.get(item.getId());
        if (nonNull(productModel)) {
            int currentAmount = productModel.getAmount().asInteger();

            //remove entirely if amount would drop to zero
            if (currentAmount < 2) {
                this.products.remove(item.getId());
            } else {
                productModel.setAmount(currentAmount - 1);
            }
            return OK;
        }
        return ITEM_404;
    }

    public void calculateCardValue() {
        this.cardValueGross = Value.of(this.products.values()
                .stream()
                .map(p -> p.getValueGross().asDecimal())
                .reduce(ZERO, BigDecimal::add));

        this.cardValueNett = Value.of(this.products.values()
                .stream()
                .map(p -> p.getValueNett().asDecimal())
                .reduce(ZERO, BigDecimal::add));
    }

}
