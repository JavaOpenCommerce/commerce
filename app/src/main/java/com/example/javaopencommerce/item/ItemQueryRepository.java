package com.example.javaopencommerce.item;

import com.example.javaopencommerce.item.dtos.ItemDto;
import io.smallrye.mutiny.Uni;
import java.util.List;

interface ItemQueryRepository {

  Uni<ItemDto> getItemById(Long id);

  Uni<List<ItemDto>> getAllItems();

  Uni<List<ItemDto>> getItemsByIdList(List<Long> ids);

}
