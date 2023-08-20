package com.example.opencommerce.app.order;

import com.example.opencommerce.app.order.query.CardDto;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import java.util.UUID;

public record CreateOrderCommand(String paymentMethod, UUID addressId, UUID userId, CardDto card, String cardId) {

    @JsonbCreator
    public CreateOrderCommand(
            @JsonbProperty("paymentMethod") String paymentMethod,
            @JsonbProperty("addressId") UUID addressId,
            @JsonbProperty("userId") UUID userId,
            @JsonbProperty("card") CardDto card) {
        this(paymentMethod, addressId, userId, card, "");
    }

    public CreateOrderCommand withCardId(String cardId) {
        return new CreateOrderCommand(paymentMethod, addressId, userId, card, cardId);
    }

}
