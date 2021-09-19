package com.example.javaopencommerce.item;

import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import com.example.javaopencommerce.image.ImageSnapshot;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
class ItemSnapshot {

  private final Long id;
  private final Value valueGross;
  private final Vat vat;
  private final ImageSnapshot image;
  private final int stock;

  @Builder.Default
  private final List<ItemDetailsSnapshot> details;

  ItemSnapshot(Long id, Value valueGross, Vat vat,
      ImageSnapshot image, int stock, List<ItemDetailsSnapshot> details) {
    this.id = id;
    this.valueGross = valueGross;
    this.vat = vat;
    this.image = image;
    this.stock = stock;
    this.details = new ArrayList<>(details);
  }
}
