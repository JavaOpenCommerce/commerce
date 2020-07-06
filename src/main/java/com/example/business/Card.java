package com.example.business;

import com.example.business.models.AddressModel;
import com.example.database.services.CardService;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.math.BigDecimal.ZERO;

@Getter
@EqualsAndHashCode
public final class Card {

    private final Map<Long, Product> products = new HashMap<>();
    private AddressModel deliveryAddress;
    private Payment payment;
    private Value cardValueNett = Value.of(ZERO);
    private Value cardValueGross = Value.of(ZERO);

    private final CardService cardService;

    public Card(CardService cardService) {
        this.cardService = cardService;
        calculateCardValue();
    }

    public void addProductToCard(Long id) {
        if (products.containsKey(id)) {
            updateProductAmount(id, products.get(id).getAmount().asInteger() + 1);
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
        int stock = cardService.checkItemStock(productId);
        if (amount <= stock) {
           product.setAmount(amount);
        } else {
            product.setAmount(stock);
        }
        if (amount <= 0) {
            products.remove(productId);
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

    public void setDeliveryAddress(Long id) {
        this.deliveryAddress = cardService.getAddressModel(id);
    }
}
