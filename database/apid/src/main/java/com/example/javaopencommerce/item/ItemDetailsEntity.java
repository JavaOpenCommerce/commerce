package com.example.javaopencommerce.item;


import com.example.javaopencommerce.image.ImageEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"images"})
public class ItemDetailsEntity {

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

    private Long id;
    private String name;
    private Locale lang;
    private String description;
    private Long itemId;

    @Builder.Default
    private final List<ImageEntity> images = new ArrayList<>();

    ItemDetails toItemDetailsModel() {
        return ItemDetails.builder()
            .id(id)
            .name(name)
            .description(description)
            .lang(lang)
            .additionalImages(
                images.stream()
                    .map(ImageEntity::toImageModel)
                    .collect(Collectors.toList()))
            .build();
    }

}
