package com.example.javaopencommerce.item;

import io.smallrye.mutiny.Uni;
import java.util.List;

interface CardRepository {

    Uni<List<Product>> getCardList(String id);

    Uni<List<Product>> saveCard(String id, List<Product> products);
}
