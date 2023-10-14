package com.example.opencommerce.infra.catalog.views;

import com.example.opencommerce.app.catalog.query.ImageDto;
import com.example.opencommerce.app.catalog.query.ProducerDto;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Value
public class FullItemView {

    Long id;
    String name;
    String description;
    ImageView image;
    List<UUID> categoryIds;
    ProducerDto producer; // screw type for now
    List<ImageView> additionalImages;
    PriceView price;
}
