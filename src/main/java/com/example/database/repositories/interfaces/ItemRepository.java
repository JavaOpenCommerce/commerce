package com.example.database.repositories.interfaces;

import com.example.database.entity.Item;
import com.example.database.entity.ItemDetails;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface ItemRepository {

    Uni<List<Item>> getAllItems();

    Uni<Item> getItemById(Long id);

    Uni<List<Item>> getItemsListByIdList(List<Long> ids);

    Uni<List<ItemDetails>> getAllItemDetails();

    Uni<List<ItemDetails>> getItemDetailsListByItemId(Long id);

    Uni<List<ItemDetails>> getItemDetailsListByIdList(List<Long> ids);

    Uni<Item> saveItem(Item item);

    Uni<ItemDetails> saveItemDetails(ItemDetails itemDetails);
}
