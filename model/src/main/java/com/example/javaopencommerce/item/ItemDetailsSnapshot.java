package com.example.javaopencommerce.item;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import com.example.javaopencommerce.image.ImageSnapshot;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
class ItemDetailsSnapshot {

    Long id;
    String name;
    String description;
    Locale lang;
    List<ImageSnapshot> additionalImages;

    public ItemDetailsSnapshot(Long id, String name, String description, Locale lang,
                               List<ImageSnapshot> additionalImages) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.lang = lang;
        this.additionalImages = new ArrayList<>(
            ofNullable(additionalImages).orElse(emptyList())
        );
    }

}
