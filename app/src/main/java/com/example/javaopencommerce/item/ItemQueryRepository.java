package com.example.javaopencommerce.item;

import com.example.javaopencommerce.item.dtos.ItemDetailsDto;
import com.example.javaopencommerce.item.dtos.ItemDto;
import java.util.List;

public interface ItemQueryRepository {

  ItemDetailsDto getItemById(Long id);

  List<ItemDto> getAllItems();

  List<ItemDto> getItemsByCategoryId(Long id);

  List<ItemDto> getShippingMethods();

  List<ItemDto> getItemsByIdList(List<Long> ids);

}
