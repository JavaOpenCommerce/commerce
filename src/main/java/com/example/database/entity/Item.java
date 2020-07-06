package com.example.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Producer producer;

    @Builder.Default
    private List<Category> category = new ArrayList<>();

    @Builder.Default
    private List<ItemDetails> details = new ArrayList<>();

}
