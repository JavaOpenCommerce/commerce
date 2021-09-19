package com.example.javaopencommerce.item;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import com.example.javaopencommerce.image.ImageEntity;
import com.example.javaopencommerce.image.ImageSnapshot;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ItemEntity {

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

    private Long id;
    private BigDecimal valueGross;
    private double vat;
    private int stock;
    private ImageEntity image;

    @Builder.Default
    private List<ItemDetailsEntity> details = new ArrayList<>();

    Item toItemModel() {
        List<ItemDetails> detailsModels = ofNullable(details).orElse(emptyList()).stream()
            .map(ItemDetailsEntity::toItemDetailsModel)
            .collect(Collectors.toList());
        return Item.builder()
            .id(id)
            .valueGross(Value.of(valueGross))
            .vat(Vat.of(vat))
            .stock(stock)
            .details(detailsModels)
            .image(image.toImageModel())
            .build();
    }

}
