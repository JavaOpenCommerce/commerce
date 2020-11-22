package com.example.utils.converters;

import com.example.business.models.ItemModel;
import com.example.business.models.PageModel;
import com.example.rest.dtos.ItemDto;
import com.example.rest.dtos.PageDto;

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
