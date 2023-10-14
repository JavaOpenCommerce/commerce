package com.example.opencommerce.infra.catalog.views;

import com.example.opencommerce.app.catalog.query.ImageDto;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class BaseItemView {

    Long id;
    String name;
    ImageView image;
    BigDecimal valueGross;

}
