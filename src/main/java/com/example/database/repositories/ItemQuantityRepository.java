package com.example.database.repositories;

import com.example.database.entity.ItemQuantity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;

public class ItemQuantityRepository implements PanacheRepository<ItemQuantity> {

    public List<ItemQuantity> getItemQuantitiesByOrderId(Long id) {
        return list("orderDetails.id", id);
    }
}
