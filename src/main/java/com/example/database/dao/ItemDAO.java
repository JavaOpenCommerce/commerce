package com.example.database.dao;

import com.example.database.entity.Item;

import java.util.List;
import java.util.Optional;

public interface ItemDAO {
    Optional<Item> getById(Long id);

    List<Item> getAll();

    Item save(Item item);

    void delete(Item item);

    void deleteById(Long id);

    List<Item> searchItemByName(String query);

    List<Item> listItemByCategoryId(Long categoryId);

    List<Item> listItemByProducerId(Long producerId);

    int getItemStock(Long id);

    boolean exists(Long id);
}
