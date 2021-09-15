package com.example.javaopencommerce.item;

import com.example.javaopencommerce.image.ImageEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

    private Long id;
    private BigDecimal valueGross;
    private double vat;
    private int stock;
    private ImageEntity image;

    @Builder.Default
    private List<ItemDetailsEntity> details = new ArrayList<>();

}