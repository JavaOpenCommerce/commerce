package com.example.opencommerce.adapters.database.catalog.sql;

import com.example.opencommerce.app.catalog.query.FullItemDto;
import com.example.opencommerce.app.catalog.query.ImageDto;
import com.example.opencommerce.app.catalog.query.ItemDto;
import com.example.opencommerce.app.catalog.query.ProducerDto;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
class ItemDtoFactory {

    ItemDto productToDto(ItemEntity itemEntity) {
        return ItemDto.builder()
                .id(itemEntity.getId())
                .name(itemEntity.getName())
                .image(toDto(itemEntity.getImage()))
                .build();
    }

    FullItemDto toFullProductDto(ItemEntity itemEntity) {
        ItemDetailsEntity details = itemEntity.getDetails();
        ProducerEntity producer = itemEntity.getProducer();
        return FullItemDto.builder()
                .id(itemEntity.getId())
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
