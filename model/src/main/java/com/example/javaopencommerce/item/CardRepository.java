package com.example.javaopencommerce.item;

import java.util.List;

interface CardRepository {

  List<Product> getCardList(String id);

  List<Product> saveCard(String id, List<Product> products);

  void flushCard(String id);
}
