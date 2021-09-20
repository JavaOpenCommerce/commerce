package com.example.javaopencommerce.item;

import com.example.javaopencommerce.image.ImageSnapshot;
import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        this.additionalImages = new ArrayList<>(additionalImages);
    }

}
