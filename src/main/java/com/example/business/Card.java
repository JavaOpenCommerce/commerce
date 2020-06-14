package com.example.business;

import com.example.business.models.AddressModel;
import com.example.business.services.CardService;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.math.BigDecimal.ZERO;

@Getter
public final class Card {

    private final Map<Long, Product> products = new HashMap<>();
    private AddressModel deliveryAddress;
    private BigDecimal cardValueNett = ZERO;
    private BigDecimal cardValueGross = ZERO;

    private final CardService cardService;

    public Card(CardService cardService) {
        this.cardService = cardService;
        calculateCardValue();
    }

    public void addProductToCard(Long id) {
        if (products.containsKey(id)) {
            updateProductAmount(id, products.get(id).getAmount() + 1);
        } else {
            Product product = Product.getProduct(cardService.getItemModel(id));
            products.put(id, product);
        }
    }

    public void removeProductById(Long id) {
        products.remove(id);
    }

    public void updateProductAmount(Long productId, int amount) {
        Product product = products.get(productId);
        if (checkStock(productId, amount)) {
           product.setAmount(amount);
        } else {
            //todo handling, exception, message?
            product.setAmount(cardService.checkItemStock(productId));
        }
        if (amount <= 0) {
            products.remove(productId);
        }
        calculateCardValue();
    }

    public void calculateCardValue() {
        this.cardValueGross = products.values()
                .stream()
                .map(Product::getValueGross)
                .reduce(ZERO, BigDecimal::add);

        this.cardValueNett = products.values()
                .stream()
                .map(Product::getValueNett)
                .reduce(ZERO, BigDecimal::add);
    }

    private boolean checkStock(Long id, int amount) {
        int itemStock = cardService.checkItemStock(id);
        return itemStock >= amount;
    }

    public void addShippingMethod(Long id) {

    }

    public void setDeliveryAddress(Long id) {
        this.deliveryAddress = cardService.getAddressModel(id);
    }
}
