package com.example.database.repositories.interfaces;

import com.example.database.entity.ItemQuantity;

import java.util.List;

public interface ItemQuantityRepository {

    List<ItemQuantity> getItemQuantitiesByOrderId(Long id);
}
