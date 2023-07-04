package com.example.javaopencommerce.order;

import java.util.List;

interface CardRepository {

    List<CardItem> getCardList(String id);

    List<CardItem> saveCard(String id, List<CardItem> items);

    void flushCard(String id);
}
