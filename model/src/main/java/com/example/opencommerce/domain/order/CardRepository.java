package com.example.opencommerce.domain.order;

import com.example.opencommerce.domain.Amount;
import com.example.opencommerce.domain.ItemId;

import java.util.Map;

public interface CardRepository {

    Map<ItemId, Amount> getCard(String id);

    void saveCard(String cardId, Card card);

    void removeCard(String id);
}
