package com.example.javaopencommerce.item;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.Value;
import lombok.Builder;

@Builder
@lombok.Value
class ProductSnapshot {

  ItemSnapshot item;
  Value valueNett;
  Value valueGross;
  Amount amount;

  Long getItemId() {
    return item.getId();
  }

}
