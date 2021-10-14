package com.example.javaopencommerce.item.dtos;

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
    private Long mainImageId;
    private List<Long> additionalImageIds;
    private BigDecimal valueGross;
    private double vat;
    private int stock;
}
