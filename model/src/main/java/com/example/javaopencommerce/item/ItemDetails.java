package com.example.javaopencommerce.item;

import com.example.javaopencommerce.image.Image;
import com.example.javaopencommerce.image.ImageSnapshot;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Value
@Builder
public class ItemDetails {

    Long id;
    String name;
    String description;
    Locale lang;
    List<Image> additionalImages;

    ItemDetailsSnapshot getSnapshot() {
        List<ImageSnapshot> imageSnapshots = ofNullable(this.additionalImages)
                .orElse(emptyList())
                .stream()
                .map(Image::getSnapshot)
                .collect(Collectors.toList());
        return new ItemDetailsSnapshot(this.id, this.name, this.description, this.lang, imageSnapshots);
    }

    static ItemDetails restore(ItemDetailsSnapshot detailsSnapshot) {
        return ItemDetails.builder()
                .id(detailsSnapshot.getId())
                .description(detailsSnapshot.getDescription())
                .lang(detailsSnapshot.getLang())
                .name(detailsSnapshot.getName())
                .additionalImages(
                        ofNullable(detailsSnapshot.getAdditionalImages())
                                .orElse(emptyList())
                                .stream()
                                .map(Image::restore)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
