package com.example.opencommerce.app.catalog.query;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;


@Value
@Builder
public class FullItemDto {

    Long id;
    List<UUID> categoryIds;
    ProducerDto producer;
    String name;
    String description;
    ImageDto mainImage;
    List<ImageDto> additionalImages;
}
