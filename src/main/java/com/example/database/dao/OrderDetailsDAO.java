package com.example.database.dao;

import com.example.database.entity.ItemQuantity;
import com.example.database.entity.OrderDetails;

import java.util.List;
import java.util.Optional;

public interface OrderDetailsDAO {
    Optional<OrderDetails> getById(Long id);

    List<OrderDetails> getAll();

    OrderDetails save(OrderDetails orderDetails);

    void delete(OrderDetails orderDetails);

    void deleteById(Long id);

    List<ItemQuantity> getItemQuantitiesByOrderId(Long id);

    ItemQuantity addNewItemQuantityToOrder(ItemQuantity itemQuantity);

    void deleteItemQuantityById(Long id);
}
