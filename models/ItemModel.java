package com.example.business.models;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@EqualsAndHashCode
public class ItemModel {

    private final Long id;
    private final String name;
    private final String description;
    private BigDecimal valueGross;
    private BigDecimal vat;
    private ProducerModel producerModel;
}
