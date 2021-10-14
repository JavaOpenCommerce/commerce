package com.example.javaopencommerce.item;

import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;

@Builder
@lombok.Value
class ItemSnapshot {

    Long id;
    Value valueGross;
    Vat vat;
    Long imageId;
    int stock;
    List<ItemDetailsSnapshot> details;

    ItemSnapshot(Long id, Value valueGross, Vat vat,
                 Long imageId, int stock, List<ItemDetailsSnapshot> details) {
        this.id = id;
        this.valueGross = valueGross;
        this.vat = vat;
        this.imageId = imageId;
        this.stock = stock;
        this.details = new ArrayList<>(details);
    }

}
