package com.example.javaopencommerce.order;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.ItemId;
import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;

@lombok.Value
class Item {

    ItemId id;
    Amount stock;
    String name;
    String imageUrl;
    Value valueGross;
    Vat vat;

    static Item empty(ItemId id, String name) {
        return new Item(id, Amount.ZERO, name, null, Value.ZERO, Vat.ZERO);
    }

    ItemSnapshot getSnapshot() {
        return new ItemSnapshot(id, stock, name, imageUrl, valueGross, vat);
    }

    record ItemSnapshot(ItemId id, Amount stock, String name, String imageUrl, Value valueGross,
                        Vat vat) {
    }
}
