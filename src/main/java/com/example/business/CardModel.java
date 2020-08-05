package com.example.business;

import com.example.business.models.AddressModel;
import com.example.business.models.ItemModel;
import com.example.business.models.ProductModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.math.BigDecimal.ZERO;

@Getter
@EqualsAndHashCode
public final class CardModel {

    private final Map<Long, ProductModel> products;
    private AddressModel deliveryAddress;
    private Payment payment;
    private Value cardValueNett = Value.of(ZERO);
    private Value cardValueGross = Value.of(ZERO);


    public CardModel(Map<Long, ProductModel> products) {
        this.products = new HashMap<>(products);
        calculateCardValue();
    }

    public void addProduct(ItemModel item, int amount) {
        if (item.getStock() < 1) {
            return;
            //todo handling, issue #6
        }

        Long id = item.getId();
        if (!products.containsKey(id)) {
            ProductModel product = ProductModel.getProduct(item);
            products.put(id, product);
        }

        updateProductAmount(id, amount, item.getStock());
    }

    private void updateProductAmount(Long productId, int amount, int stock) {
        ProductModel product = products.get(productId);

        if (amount <= 0) {
            products.remove(productId);
        } else if (amount <= stock) {
            product.setAmount(amount);
        } else {
            product.setAmount(stock);
        }
        calculateCardValue();
    }

    public void calculateCardValue() {
        this.cardValueGross = Value.of(products.values()
                .stream()
                .map(p -> p.getValueGross().asDecimal())
                .reduce(ZERO, BigDecimal::add));

        this.cardValueNett = Value.of(products.values()
                .stream()
                .map(p -> p.getValueNett().asDecimal())
                .reduce(ZERO, BigDecimal::add));
    }

}
