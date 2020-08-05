package com.example.database.repositories.interfaces;

import com.example.database.entity.Product;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface CardRepository {

    Uni<List<Product>> getCardList(String id);

    Uni<List<Product>> saveCard(String id, List<Product> products);
}
