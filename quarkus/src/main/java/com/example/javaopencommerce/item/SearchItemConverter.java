package com.example.javaopencommerce.item;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import com.example.javaopencommerce.utils.converters.ImageConverter;
import java.util.List;
import java.util.stream.Collectors;

public interface SearchItemConverter {

    static SearchItem convertToSearchItem(Item item) {

        List<SearchItemDetails> searchItemDetails =ofNullable(item.getDetails()).orElse(emptyList())
                .stream()
                .map(SearchItemConverter::convertToSearchDetails)
                .collect(Collectors.toList());

        return SearchItem.builder()
                .id(item.getId())
                .image(ImageConverter.convertToDto(item.getImage()))
                .details(searchItemDetails)
                .valueGross(item.getValueGross().asDecimal().doubleValue())
                .build();
    }

    static SearchItemDetails convertToSearchDetails(ItemDetails details) {
        return SearchItemDetails.builder()
                .lang(details.getLang().toString())
                .name(details.getName())
                .description(details.getDescription())
                .build();
    }
}
