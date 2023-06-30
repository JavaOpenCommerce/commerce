package com.example.javaopencommerce.catalog.dtos;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;


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
  BigDecimal valueGross;
  double vat;
  int stock;
}
