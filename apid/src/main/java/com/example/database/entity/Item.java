package com.example.database.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Item {

    private Long id;
    private BigDecimal valueGross;
    private double vat;
    private int stock;
    private Image image;
    private Long producerId;

    @Builder.Default
    private List<Long> categoryIds = new ArrayList<>();

    @Builder.Default
    private List<ItemDetails> details = new ArrayList<>();

}
