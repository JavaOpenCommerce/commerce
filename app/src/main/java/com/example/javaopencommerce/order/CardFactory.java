package com.example.javaopencommerce.order;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.ItemId;
import com.example.javaopencommerce.catalog.query.ItemQueryRepository;
import com.example.javaopencommerce.catalog.query.ItemDto;
import com.example.javaopencommerce.warehouse.query.WarehouseQueryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class CardFactory {

    private final ItemQueryRepository itemRepository;
    private final WarehouseQueryRepository warehouseRepository;
    private final ItemMapper itemMapper;


    CardFactory(ItemQueryRepository itemRepository, WarehouseQueryRepository warehouseRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.warehouseRepository = warehouseRepository;
        this.itemMapper = itemMapper;
    }

    Card restoreCard(Map<ItemId, Amount> cardBody) {
        List<ItemId> itemIds = cardBody.keySet()
                .stream()
                .toList();

        List<ItemDto> items = itemRepository.getItemsByIdList(itemIds);
        Map<ItemId, Amount> itemStocks = warehouseRepository.getAvailableStocksByItemIds(itemIds);

        List<CardItem> cardItems = new ArrayList<>();


        for (Map.Entry<ItemId, Amount> cardItem : cardBody.entrySet()) {
            Optional<ItemDto> matchedItem = items.stream()
                    .filter(item -> item.getId()
                            .equals(cardItem.getKey()
                                    .asLong()))
                    .findFirst();

            ItemId itemId = cardItem.getKey();
            if (matchedItem.isEmpty() || !itemStocks.containsKey(itemId)) {
                cardItems.add(CardItem.empty(itemId, ""));
                continue;
            }

            Item matchedItemModel = itemMapper.toModel(matchedItem.get(), itemStocks.get(itemId));
            cardItems.add(CardItem.withAmount(matchedItemModel, cardItem.getValue()));
        }
        return Card.ofProducts(cardItems);
    }
}
