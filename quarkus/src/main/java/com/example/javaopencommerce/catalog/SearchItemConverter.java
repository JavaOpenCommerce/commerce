package com.example.javaopencommerce.catalog;

import com.example.javaopencommerce.catalog.query.FullItemDto;

import java.util.HashSet;
import java.util.UUID;

interface SearchItemConverter {

    static SearchItem convertToSearchItem(FullItemDto item) {
        return new SearchItem(item.getId(), item.getName(), item.getDescription(),
                item.getValueGross()
                        .doubleValue(), item.getProducer()
                .getId(),
                new HashSet<>(item.getCategoryIds()
                        .stream()
                        .map(UUID::toString)
                        .toList()));
    }
}
