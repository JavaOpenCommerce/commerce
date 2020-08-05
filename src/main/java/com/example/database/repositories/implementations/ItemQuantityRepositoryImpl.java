package com.example.database.repositories.implementations;

import com.example.database.entity.ItemQuantity;
import com.example.database.repositories.interfaces.ItemQuantityRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ItemQuantityRepositoryImpl implements ItemQuantityRepository {

    @Override
    public List<ItemQuantity> getItemQuantitiesByOrderId(Long id) {
        //todo
        return null;
    }
}
