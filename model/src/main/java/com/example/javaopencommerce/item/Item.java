package com.example.javaopencommerce.item;


import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import com.example.javaopencommerce.image.Image;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@lombok.Value
@Builder
public class Item {

    Long id;
    Value valueGross;
    Vat vat;
    Image image;
    List<ItemDetails> details;
    int stock;

    void addDetails(List<ItemDetails> newDetails) {
        this.details.addAll(ofNullable(newDetails).orElse(emptyList()));
    }

    static Item restore(ItemSnapshot itemSnapshot) {
        return Item.builder()
                .id(itemSnapshot.getId())
                .image(Image.restore(itemSnapshot.getImage()))
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
                this.id, this.valueGross, this.vat, this.image.getSnapshot(), this.stock, detailsSnapshots
        );
    }
}
