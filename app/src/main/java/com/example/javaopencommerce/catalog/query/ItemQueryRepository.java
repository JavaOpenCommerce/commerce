package com.example.javaopencommerce.catalog.query;

import com.example.javaopencommerce.ItemId;

import java.util.List;

public interface ItemQueryRepository {

    FullItemDto getItemById(ItemId id);

    List<FullItemDto> getAllFullItems();

    List<ItemDto> getShippingMethods();

    List<ItemDto> getItemsByIdList(List<ItemId> ids);

}
