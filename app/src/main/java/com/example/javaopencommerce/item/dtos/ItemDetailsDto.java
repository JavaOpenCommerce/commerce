package com.example.javaopencommerce.item.dtos;

import com.example.javaopencommerce.image.ImageDto;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDetailsDto {

    private Long id;
    private String name;
    private String description;
    private ImageDto mainImage;
    private List<ImageDto> additionalImages;
    private BigDecimal valueGross;
    private double vat;
    private int stock;
}
