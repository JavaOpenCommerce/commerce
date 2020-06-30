package com.example.utils.converters;

import com.example.business.models.ItemDetailModel;
import com.example.business.models.ItemModel;
import com.example.elasticsearch.SearchDetails;
import com.example.elasticsearch.SearchItem;

import java.util.Set;
import java.util.stream.Collectors;

public interface SearchItemConverter {

    static SearchItem convertToSearchItem(ItemModel item) {

        Set<Long> categoryIds = item.getCategory().stream()
                .map(c -> c.getId())
                .collect(Collectors.toSet());

        Set<SearchDetails> searchDetails = item.getDetails().stream()
                .map(itemDetail -> convertToSearchDetails(itemDetail))
                .collect(Collectors.toSet());

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
