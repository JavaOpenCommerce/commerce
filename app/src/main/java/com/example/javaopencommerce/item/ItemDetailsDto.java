package com.example.javaopencommerce.item;

import com.example.javaopencommerce.image.ImageDto;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ItemDetailsDto {

    static ItemDetailsDto fromSnapshot(ItemSnapshot itemSnapshot, ItemDetailsSnapshot details) {

        List<ImageDto> additionalImages = details.getAdditionalImages().stream()
            .map(ImageDto::fromSnapshot)
            .collect(Collectors.toList());

        return ItemDetailsDto.builder()
            .id(itemSnapshot.getId())
            .stock(itemSnapshot.getStock())
            .valueGross(itemSnapshot.getValueGross().asDecimal())
            .vat(itemSnapshot.getVat().asDouble())
            .description(details.getDescription())
            .name(details.getName())
            .mainImage(ImageDto.fromSnapshot(itemSnapshot.getImage()))
            .additionalImages(additionalImages)
            .build();
    }

    private Long id;
    private String name;
    private String description;
    private ImageDto mainImage;
    private List<ImageDto> additionalImages;
    private BigDecimal valueGross;
    private double vat;
    private int stock;
}
