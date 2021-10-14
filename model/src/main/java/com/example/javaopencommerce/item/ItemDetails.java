package com.example.javaopencommerce.item;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import java.util.List;
import java.util.Locale;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
class ItemDetails {

    Long id;
    String name;
    String description;
    Locale lang;
    List<Long> additionalImageIds;

    ItemDetailsSnapshot getSnapshot() {
        return new ItemDetailsSnapshot(this.id, this.name, this.description, this.lang, this.additionalImageIds);
    }

    static ItemDetails restore(ItemDetailsSnapshot detailsSnapshot) {
        return ItemDetails.builder()
                .id(detailsSnapshot.getId())
                .description(detailsSnapshot.getDescription())
                .lang(detailsSnapshot.getLang())
                .name(detailsSnapshot.getName())
                .additionalImageIds(
                        ofNullable(detailsSnapshot.getAdditionalImageIds())
                                .orElse(emptyList()))
                .build();
    }
}
