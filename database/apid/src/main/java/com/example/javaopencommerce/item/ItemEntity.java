package com.example.javaopencommerce.item;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
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
class ItemEntity {

    private Long id;
    private BigDecimal valueGross;
    private double vat;
    private int stock;
    private Long imageId;
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
                .imageId(this.imageId)
                .build();
    }

    static ItemEntity fromSnapshot(ItemSnapshot itemSnapshot) {
        return ItemEntity.builder()
                .id(itemSnapshot.getId())
                .valueGross(itemSnapshot.getValueGross().asDecimal())
                .vat(itemSnapshot.getVat().asDouble())
                .stock(itemSnapshot.getStock())
                .imageId(itemSnapshot.getImageId())
                .build();
    }

}
