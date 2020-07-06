package com.example.utils.converters;

import com.example.business.models.ItemDetailModel;
import com.example.business.models.ItemModel;
import com.example.elasticsearch.SearchDetails;
import com.example.elasticsearch.SearchItem;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

public interface SearchItemConverter {

    static SearchItem convertToSearchItem(ItemModel item) {

        List<Long> categoryIds = ofNullable(item.getCategory()).orElse(emptyList())
                .stream()
                .map(c -> c.getId())
                .collect(Collectors.toList());

        List<SearchDetails> searchDetails =ofNullable(item.getDetails()).orElse(emptyList())
                .stream()
                .map(itemDetail -> convertToSearchDetails(itemDetail))
                .collect(Collectors.toList());

        return SearchItem.builder()
                .id(item.getId())
                .image(ImageConverter.convertToDto(item.getImage()))
                .categoryIds(categoryIds)
                .producerId(item.getProducer().getId())
                .details(searchDetails)
                .valueGross(item.getValueGross().asDecimal().doubleValue())
                .build();
    }

    static SearchDetails convertToSearchDetails(ItemDetailModel details) {
        return SearchDetails.builder()
                .lang(details.getLang())
                .name(details.getName())
                .description(details.getDescription())
                .build();
    }
}
