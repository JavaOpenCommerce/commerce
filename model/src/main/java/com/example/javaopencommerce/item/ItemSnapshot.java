package com.example.javaopencommerce.item;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

    @lombok.Value
    @Builder
    static class ItemDetailsSnapshot {

        Long id;
        String name;
        String description;
        Locale lang;
        List<Long> additionalImageIds;

        public ItemDetailsSnapshot(Long id, String name, String description, Locale lang,
            List<Long> additionalImageIds) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.lang = lang;
            this.additionalImageIds = new ArrayList<>(
                ofNullable(additionalImageIds).orElse(emptyList())
            );
        }
    }
}
