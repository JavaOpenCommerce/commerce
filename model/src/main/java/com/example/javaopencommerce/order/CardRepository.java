package com.example.javaopencommerce.order;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.ItemId;

import java.util.Map;

public interface CardRepository {

    Map<ItemId, Amount> getCard(String id);

    void saveCard(String cardId, Card card);

    void removeCard(String id);
}
