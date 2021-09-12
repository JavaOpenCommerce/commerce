package com.example.javaopencommerce.order;

import io.smallrye.mutiny.Uni;
import java.util.List;

public interface CardRepository {

    Uni<List<CardProduct>> getCardList(String id);

    Uni<List<CardProduct>> saveCard(String id, List<CardProduct> products);
}
