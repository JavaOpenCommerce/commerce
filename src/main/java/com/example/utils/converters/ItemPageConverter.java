package com.example.utils.converters;

import com.example.javaopencommerce.PageDto;
import com.example.javaopencommerce.PageModel;
import com.example.javaopencommerce.item.ItemDto;
import com.example.javaopencommerce.item.ItemModel;
import java.util.List;
import java.util.stream.Collectors;

public interface ItemPageConverter {

    static PageDto<ItemDto> convertToDto(PageModel<ItemModel> model, String lang, String defaultLang) {

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
