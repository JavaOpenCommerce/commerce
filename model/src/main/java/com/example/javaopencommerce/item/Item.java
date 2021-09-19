package com.example.javaopencommerce.item;


import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import com.example.javaopencommerce.image.Image;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Item {

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

    private final Long id;
    private Value valueGross;
    private Vat vat;
    private Image image;
    private final List<ItemDetails> details;
    private int stock;

    void addDetails(List<ItemDetails> newDetails) {
        details.addAll(ofNullable(newDetails).orElse(emptyList()));
    }

    ItemSnapshot getSnapshot() {
        List<ItemDetailsSnapshot> detailsSnapshots = details.stream()
            .map(ItemDetails::getSnapshot)
            .collect(Collectors.toList());
        return new ItemSnapshot(
            id, valueGross, vat, image.getSnapshot(), stock,detailsSnapshots
        );
    }
}
