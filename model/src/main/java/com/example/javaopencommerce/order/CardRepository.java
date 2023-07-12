package com.example.javaopencommerce.order;

interface CardRepository {

    Card getCard(String id);

    Card saveCard(String cardId, Card card);

    void flushCard(String id);
}
