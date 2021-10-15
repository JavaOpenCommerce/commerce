package com.example.javaopencommerce.item;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import com.example.javaopencommerce.image.ImageDto;
import java.util.List;
import java.util.stream.Collectors;

interface SearchItemConverter {

    static SearchItem convertToSearchItem(ItemSnapshot item) {
        List<SearchItemDetails> searchItemDetails = ofNullable(item.getDetails()).orElse(emptyList())
                .stream()
                .map(SearchItemConverter::convertToSearchDetails)
                .collect(Collectors.toList());

        return SearchItem.builder()
                .id(item.getId())
                .imageId(item.getImageId())
                .details(searchItemDetails)
                .valueGross(item.getValueGross().asDecimal().doubleValue())
                .build();
    }

    static SearchItemDetails convertToSearchDetails(ItemDetailsSnapshot details) {
        return SearchItemDetails.builder()
                .lang(details.getLang().toString())
                .name(details.getName())
                .description(details.getDescription())
                .build();
    }
}
