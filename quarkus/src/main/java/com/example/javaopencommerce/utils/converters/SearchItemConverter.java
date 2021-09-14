package com.example.javaopencommerce.utils.converters;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import com.example.javaopencommerce.elasticsearch.SearchDetails;
import com.example.javaopencommerce.elasticsearch.SearchItem;
import com.example.javaopencommerce.item.ItemDetails;
import com.example.javaopencommerce.item.Item;
import java.util.List;
import java.util.stream.Collectors;

public interface SearchItemConverter {

    static SearchItem convertToSearchItem(Item item) {

        List<SearchDetails> searchDetails =ofNullable(item.getDetails()).orElse(emptyList())
                .stream()
                .map(SearchItemConverter::convertToSearchDetails)
                .collect(Collectors.toList());

        return SearchItem.builder()
                .id(item.getId())
                .image(ImageConverter.convertToDto(item.getImage()))
                .details(searchDetails)
                .valueGross(item.getValueGross().asDecimal().doubleValue())
                .build();
    }

    static SearchDetails convertToSearchDetails(ItemDetails details) {
        return SearchDetails.builder()
                .lang(details.getLang().toString())
                .name(details.getName())
                .description(details.getDescription())
                .build();
    }
}
