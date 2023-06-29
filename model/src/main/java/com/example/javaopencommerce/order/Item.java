package com.example.javaopencommerce.order;

import com.example.javaopencommerce.Amount;
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

  ItemSnapshot getSnapshot() {
    return new ItemSnapshot(id, stock, name, imageUrl, valueGross, vat);
  }

  record ItemSnapshot(ItemId id, Amount stock, String name, String imageUrl, Value valueGross,
                      Vat vat) {

  }
}
