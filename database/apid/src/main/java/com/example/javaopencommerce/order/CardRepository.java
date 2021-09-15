package com.example.javaopencommerce.order;

import io.smallrye.mutiny.Uni;
import java.util.List;

public interface CardRepository {

    Uni<List<CardProductEntity>> getCardList(String id);

    Uni<List<CardProductEntity>> saveCard(String id, List<CardProductEntity> products);
}
