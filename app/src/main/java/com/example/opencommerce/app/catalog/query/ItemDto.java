package com.example.opencommerce.app.catalog.query;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class ItemDto {

    Long id;
    String name;
    ImageDto image;
}
