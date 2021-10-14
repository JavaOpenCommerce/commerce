package com.example.javaopencommerce.item;


import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@lombok.Value
@Getter(AccessLevel.NONE)
@Builder
class Item {

    Long id;
    Value valueGross;
    Vat vat;
    Long imageId;
    List<ItemDetails> details;
    int stock;

    void addDetails(List<ItemDetails> newDetails) {
        this.details.addAll(ofNullable(newDetails).orElse(emptyList()));
    }

    static Item restore(ItemSnapshot itemSnapshot) {
        return Item.builder()
                .id(itemSnapshot.getId())
                .imageId(itemSnapshot.getImageId())
                .stock(itemSnapshot.getStock())
                .valueGross(itemSnapshot.getValueGross())
                .vat(itemSnapshot.getVat())
                .details(
                        itemSnapshot.getDetails().stream()
                                .map(ItemDetails::restore)
                                .collect(Collectors.toList()))
                .build();
    }

    ItemSnapshot getSnapshot() {
        List<ItemDetailsSnapshot> detailsSnapshots = this.details.stream()
                .map(ItemDetails::getSnapshot)
                .collect(Collectors.toList());
        return new ItemSnapshot(
                this.id, this.valueGross, this.vat, this.imageId, this.stock, detailsSnapshots
        );
    }

    Value getValueGross() {
        return valueGross;
    }

    Vat getVat() {
        return vat;
    }
}
