package com.example.javaopencommerce.catalog;

import com.example.javaopencommerce.catalog.dtos.FullItemDto;
import com.example.javaopencommerce.catalog.dtos.ImageDto;
import com.example.javaopencommerce.catalog.dtos.ItemDto;
import com.example.javaopencommerce.catalog.dtos.ProducerDto;

class ItemDtoFactory {

    ItemDto productToDto(ItemEntity itemEntity) {
        return ItemDto.builder()
                .id(itemEntity.getId())
                .stock(itemEntity.getStock())
                .valueGross(itemEntity.getValueGross())
                .vat(itemEntity.getVat())
                .name(itemEntity.getName())
                .image(toDto(itemEntity.getImage()))
                .build();
    }

    FullItemDto toFullProductDto(ItemEntity itemEntity) {
        ItemDetailsEntity details = itemEntity.getDetails();
        ProducerEntity producer = itemEntity.getProducer();
        return FullItemDto.builder()
                .id(itemEntity.getId())
                .stock(itemEntity.getStock())
                .valueGross(itemEntity.getValueGross())
                .vat(itemEntity.getVat())
                .description(details.getDescription())
                .name(itemEntity.getName())
                .mainImage(toDto(itemEntity.getImage()))
                .categoryIds(itemEntity.getCategoryIds())
                .producer(toDto(producer))
                .additionalImages(details.getAdditionalImages()
                        .stream()
                        .map(this::toDto)
                        .toList())
                .build();
    }

    private ImageDto toDto(ImageEntity imageEntity) {
        return new ImageDto(imageEntity.getId(), imageEntity.getAlt(), imageEntity.getUrl());
    }

    private ProducerDto toDto(ProducerEntity producerEntity) {
        return new ProducerDto(producerEntity.getId(), producerEntity.getName(),
                producerEntity.getImageUrl());
    }
}
