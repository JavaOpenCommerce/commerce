package com.example.javaopencommerce.catalog.query;

import com.example.javaopencommerce.ItemId;
import com.example.javaopencommerce.catalog.query.FullItemDto;
import com.example.javaopencommerce.catalog.query.ItemDto;

import java.util.List;

public interface ItemQueryRepository {

    FullItemDto getItemById(ItemId id);

    List<FullItemDto> getAllFullItems();

    List<ItemDto> getShippingMethods();

    List<ItemDto> getItemsByIdList(List<ItemId> ids);

}
