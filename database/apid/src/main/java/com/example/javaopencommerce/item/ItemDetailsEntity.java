package com.example.javaopencommerce.item;


import com.example.javaopencommerce.image.ImageEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"images"})
public class ItemDetailsEntity {

    private Long id;

    private String name;
    private Locale lang;
    private String description;
    private Long itemId;
    @Builder.Default
    private final List<ImageEntity> images = new ArrayList<>();

    public ItemDetails toItemDetailsModel() {
        return ItemDetails.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .lang(this.lang)
                .additionalImages(
                        this.images.stream()
                                .map(ImageEntity::toImageModel)
                                .collect(Collectors.toList()))
                .build();
    }

    static ItemDetailsEntity fromSnapshot(ItemDetailsSnapshot detailsSnapshot, Long itemId) {
        List<ImageEntity> imageEntities = detailsSnapshot.getAdditionalImages().stream()
                .map(ImageEntity::fromSnapshot)
                .collect(Collectors.toList());
        return ItemDetailsEntity.builder()
                .id(detailsSnapshot.getId())
                .description(detailsSnapshot.getDescription())
                .name(detailsSnapshot.getName())
                .lang(detailsSnapshot.getLang())
                .images(imageEntities)
                .itemId(itemId)
                .build();
    }

}
