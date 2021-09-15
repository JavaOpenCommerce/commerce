package com.example.javaopencommerce.database.services;

import static io.smallrye.mutiny.Uni.combine;
import static java.util.Collections.emptyList;

import com.example.javaopencommerce.image.ImageRepository;
import com.example.javaopencommerce.item.Item;
import com.example.javaopencommerce.item.ItemDetailsEntity;
import com.example.javaopencommerce.item.ItemEntity;
import com.example.javaopencommerce.item.ItemRepository;
import com.example.javaopencommerce.quarkus.exceptions.ItemExceptionEntity;
import com.example.javaopencommerce.quarkus.exceptions.OutOfStockException;
import com.example.javaopencommerce.utils.converters.ImageConverter;
import com.example.javaopencommerce.utils.converters.ItemConverter;
import io.smallrye.mutiny.Uni;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class ItemService {

    private final ItemRepository itemRepository;
    private final ImageRepository imageRepository;

    public ItemService(ItemRepository itemRepository, ImageRepository imageRepository) {
        this.itemRepository = itemRepository;
        this.imageRepository = imageRepository;
    }

    public Uni<Item> getItemById(Long id) {
        Uni<ItemEntity> itemUni = this.itemRepository.getItemById(id);
        Uni<List<ItemDetailsEntity>> itemDetailsUni = this.itemRepository.getItemDetailsListByItemId(id);

        return combine().all()
                .unis(itemUni, itemDetailsUni)
                .combinedWith(ItemConverter::convertToModel);
    }

    public Uni<List<Item>> getAllItems() {
        Uni<List<ItemEntity>> itemsUni = this.itemRepository.getAllItems();
        Uni<List<ItemDetailsEntity>> itemDetailsUni = this.itemRepository.getAllItemDetails();

        return combine().all()
                .unis(itemsUni, itemDetailsUni)
                .combinedWith(
                        ItemConverter::convertToItemModelList);
    }

    public Uni<List<Item>> getItemsListByIdList(List<Long> ids) {
        Uni<List<ItemEntity>> itemsUni = this.itemRepository.getItemsListByIdList(ids);
        Uni<List<ItemDetailsEntity>> itemDetailsUni = this.itemRepository.getItemDetailsListByIdList(ids);

        return combine().all()
                .unis(itemsUni, itemDetailsUni)
                .combinedWith(ItemConverter::convertToItemModelList);
    }

    public Uni<Integer> changeStock(Long id, int amount) {
        Uni<Integer> itemStock = this.itemRepository.getItemStock(id);
        return itemStock.flatMap(stock -> {
            if (stock == -1) {
                throw new OutOfStockException(
                        ItemExceptionEntity.create(id, "Item is out of stock! Rollback"));
            } else if (stock < amount) {
                throw new OutOfStockException(
                        ItemExceptionEntity.create(id, "Not enough items in stock! Rollback"));
            } else {
                return this.itemRepository.changeItemStock(id, stock - amount);
            }
        });
    }

    @Transactional
    public Uni<Item> addNewItem(Uni<Item> item) {
        Uni<ItemEntity> savedItem = item
                .map(i -> {
                    this.imageRepository
                            .saveImage(ImageConverter.convertModelToEntity(i.getImage()))
                            .await().indefinitely();
                    return ItemConverter.convertModelToEntity(i);
                })
                .flatMap(this.itemRepository::saveItem);

        return savedItem
                .map(i -> ItemConverter.convertToModel(i, emptyList()));
    }
}