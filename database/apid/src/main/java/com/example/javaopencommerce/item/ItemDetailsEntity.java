package com.example.javaopencommerce.item;


import com.example.javaopencommerce.item.Item.ItemDetails;
import com.example.javaopencommerce.item.ItemSnapshot.ItemDetailsSnapshot;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"imageIds"})
class ItemDetailsEntity {

    private Long id;

    private String name;
    private Locale lang;
    private String description;
    private Long itemId;
    @Builder.Default
    private final List<Long> imageIds = new ArrayList<>();

    public ItemDetails toItemDetailsModel() {
        return ItemDetails.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .lang(this.lang)
                .additionalImageIds(this.imageIds)
                .build();
    }

    static ItemDetailsEntity fromSnapshot(ItemDetailsSnapshot detailsSnapshot, Long itemId) {
        return ItemDetailsEntity.builder()
                .id(detailsSnapshot.getId())
                .description(detailsSnapshot.getDescription())
                .name(detailsSnapshot.getName())
                .lang(detailsSnapshot.getLang())
                .imageIds(detailsSnapshot.getAdditionalImageIds())
                .itemId(itemId)
                .build();
    }

}
