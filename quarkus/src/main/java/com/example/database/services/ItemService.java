package com.example.database.services;

import static io.smallrye.mutiny.Uni.combine;
import static java.util.Collections.emptyList;

import com.example.javaopencommerce.category.Category;
import com.example.javaopencommerce.category.CategoryRepository;
import com.example.javaopencommerce.image.ImageRepository;
import com.example.javaopencommerce.item.Item;
import com.example.javaopencommerce.item.ItemDetails;
import com.example.javaopencommerce.item.ItemModel;
import com.example.javaopencommerce.item.ItemRepository;
import com.example.javaopencommerce.producer.Producer;
import com.example.javaopencommerce.producer.ProducerRepository;
import com.example.quarkus.exceptions.ItemExceptionEntity;
import com.example.quarkus.exceptions.OutOfStockException;
import com.example.utils.converters.ImageConverter;
import com.example.utils.converters.ItemConverter;
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

    public Uni<ItemModel> getItemById(Long id) {
        Uni<Item> itemUni = this.itemRepository.getItemById(id);
        Uni<List<ItemDetails>> itemDetailsUni = this.itemRepository.getItemDetailsListByItemId(id);

        return combine().all()
                .unis(itemUni, itemDetailsUni)
                .combinedWith(ItemConverter::convertToModel);
    }

    public Uni<List<ItemModel>> getAllItems() {
        Uni<List<Item>> itemsUni = this.itemRepository.getAllItems();
        Uni<List<ItemDetails>> itemDetailsUni = this.itemRepository.getAllItemDetails();

        return combine().all()
                .unis(itemsUni, itemDetailsUni)
                .combinedWith(
                        ItemConverter::convertToItemModelList);
    }

    public Uni<List<ItemModel>> getItemsListByIdList(List<Long> ids) {
        Uni<List<Item>> itemsUni = this.itemRepository.getItemsListByIdList(ids);
        Uni<List<ItemDetails>> itemDetailsUni = this.itemRepository.getItemDetailsListByIdList(ids);

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
    public Uni<ItemModel> addNewItem(Uni<ItemModel> item) {
        Uni<Item> savedItem = item
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