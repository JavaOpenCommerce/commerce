package com.example.javaopencommerce.item;

import com.example.javaopencommerce.item.dtos.ItemDetailsDto;
import com.example.javaopencommerce.item.dtos.ItemDto;
import io.smallrye.mutiny.Uni;
import java.util.List;

public interface ItemQueryRepository {

  Uni<ItemDetailsDto> getItemById(Long id);

  Uni<List<ItemDto>> getAllItems();

  Uni<List<ItemDto>> getShippingMethods();

  Uni<List<ItemDto>> getItemsListByIdList(List<Long> ids);

}
