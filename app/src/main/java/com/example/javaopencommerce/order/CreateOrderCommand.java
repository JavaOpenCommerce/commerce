package com.example.javaopencommerce.order;

import com.example.javaopencommerce.order.query.CardDto;

import java.util.UUID;

record CreateOrderCommand(String paymentMethod, UUID addressId, UUID userId, CardDto card, String cardId) {

    CreateOrderCommand withCardId(String cardId) {
        return new CreateOrderCommand(paymentMethod, addressId, userId, card, cardId);
    }

}
