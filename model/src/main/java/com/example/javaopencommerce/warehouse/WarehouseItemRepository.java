package com.example.javaopencommerce.warehouse;

import com.example.javaopencommerce.ItemId;

interface WarehouseItemRepository {

    ItemStock getItemById(ItemId id);

    void updateStock(ItemStock item);

    void save(ItemStock item);
}
