package com.example.rest.dtos;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private Long id;
    private String name;
    private int stock;
    private BigDecimal valueGross;
    private ImageDto image;
    private double vat;
    private ProducerDto producer;

}
