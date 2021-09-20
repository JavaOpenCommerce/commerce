package com.example.javaopencommerce.item;

import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import com.example.javaopencommerce.image.ImageEntity;
import com.example.javaopencommerce.image.ImageSnapshot;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ItemEntity {

    private Long id;
    private BigDecimal valueGross;
    private double vat;
    private int stock;
    private ImageEntity image;
    @Builder.Default
    private List<ItemDetailsEntity> details = new ArrayList<>();

    public Item toItemModel() {
        List<ItemDetails> detailsModels = ofNullable(this.details).orElse(emptyList()).stream()
                .map(ItemDetailsEntity::toItemDetailsModel)
                .collect(Collectors.toList());
        return Item.builder()
                .id(this.id)
                .valueGross(Value.of(this.valueGross))
                .vat(Vat.of(this.vat))
                .stock(this.stock)
                .details(detailsModels)
                .image(this.image.toImageModel())
                .build();
    }

    static ItemEntity fromSnapshot(ItemSnapshot itemSnapshot) {
        ImageSnapshot img = ofNullable(itemSnapshot)
                .map(ItemSnapshot::getImage)
                .orElse(ImageSnapshot.builder().build());
        ImageEntity image = ImageEntity.builder()
                .id(img.getId())
                .alt(img.getAlt())
                .url(img.getUrl())
                .build();
        return ItemEntity.builder()
                .id(itemSnapshot.getId())
                .valueGross(itemSnapshot.getValueGross().asDecimal())
                .vat(itemSnapshot.getVat().asDouble())
                .stock(itemSnapshot.getStock())
                .image(image)
                .build();
    }

}
