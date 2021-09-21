package com.example.javaopencommerce.item;

import com.example.javaopencommerce.image.ImageDto;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemDto {

    static ItemDto fromSnapshot(ItemSnapshot itemSnapshot, ItemDetailsSnapshot itemDetailsSnapshot) {
        return ItemDto.builder()
            .id(itemSnapshot.getId())
            .stock(itemSnapshot.getStock())
            .valueGross(itemSnapshot.getValueGross().asDecimal())
            .vat(itemSnapshot.getVat().asDouble())
            .name(itemDetailsSnapshot.getName())
            .image(ImageDto.fromSnapshot(itemSnapshot.getImage()))
            .build();
    }

    private Long id;
    private String name;
    private int stock;
    private BigDecimal valueGross;
    private ImageDto image;
    private double vat;

}
