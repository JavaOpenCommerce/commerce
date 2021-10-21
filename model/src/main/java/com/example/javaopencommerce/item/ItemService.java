package com.example.javaopencommerce.item;

import com.example.javaopencommerce.item.exceptions.ItemExceptionBody;
import com.example.javaopencommerce.item.exceptions.OutOfStockException;
import io.smallrye.mutiny.Uni;

class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Uni<Integer> changeStock(Long id, int amount) {
        Uni<Integer> itemStock = this.itemRepository.getItemStock(id);
        return itemStock.flatMap(stock -> {
            if (stock == -1) {
                throw new OutOfStockException(
                        ItemExceptionBody.create(id, "Item is out of stock! Rollback"));
            } else if (stock < amount) {
                throw new OutOfStockException(
                        ItemExceptionBody.create(id, "Not enough items in stock! Rollback"));
            } else {
                return this.itemRepository.changeItemStock(id, stock - amount);
            }
        });
    }
}