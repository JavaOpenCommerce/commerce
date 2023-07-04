package com.example.javaopencommerce.catalog;

import com.example.javaopencommerce.catalog.dtos.FullItemDto;
import com.example.javaopencommerce.catalog.dtos.ItemDto;

import java.util.List;

public interface ItemQueryRepository {

    FullItemDto getItemById(Long id);

    List<FullItemDto> getAllFullItems();

    List<ItemDto> getShippingMethods();

    List<ItemDto> getItemsByIdList(List<Long> ids);

}
