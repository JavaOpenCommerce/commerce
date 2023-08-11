package com.example.opencommerce.domain.order;

import com.example.opencommerce.domain.Amount;
import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.Value;
import com.example.opencommerce.domain.Vat;

@lombok.Value
public class Item {

    ItemId id;
    Amount stock;
    String name;
    String imageUrl;
    Value valueGross;
    Vat vat;

    static Item empty(ItemId id, String name) {
        return new Item(id, Amount.ZERO, name, null, Value.ZERO, Vat.ZERO);
    }

    public ItemSnapshot getSnapshot() {
        return new ItemSnapshot(id, stock, name, imageUrl, valueGross, vat);
    }

    public record ItemSnapshot(ItemId id, Amount stock, String name, String imageUrl, Value valueGross,
                               Vat vat) {
    }
}
