package com.example.javaopencommerce.utils.converters;

import com.example.javaopencommerce.PageDto;
import com.example.javaopencommerce.Page;
import com.example.javaopencommerce.item.ItemDto;
import com.example.javaopencommerce.item.Item;
import java.util.List;
import java.util.stream.Collectors;

public interface ItemPageConverter {

    static PageDto<ItemDto> convertToDto(Page<Item> model, String lang, String defaultLang) {

        List<ItemDto> items = model.getItems().stream()
                .map(i -> ItemConverter.convertToDto(i, lang, defaultLang))
                .collect(Collectors.toList());

        return PageDto.<ItemDto>builder()
                .pageCount(model.getPageCount())
                .pageNumber(model.getPageNumber())
                .pageSize(model.getPageSize())
                .totalElementsCount(model.getTotalElementsCount())
                .items(items)
                .build();

    }
}
