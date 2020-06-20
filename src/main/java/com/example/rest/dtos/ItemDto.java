package com.example.rest.dtos;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@EqualsAndHashCode
public class ItemDto {

    private final Long id;
    private final String name;
    private final String description;
    private BigDecimal valueGross;
    private double vat;
    //private ProducerModel producerModel;

}
