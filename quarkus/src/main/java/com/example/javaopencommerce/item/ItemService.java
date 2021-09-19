package com.example.javaopencommerce.item;

import static io.smallrye.mutiny.Uni.combine;
import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.image.ImageEntity;
import com.example.javaopencommerce.image.ImageRepository;
import com.example.javaopencommerce.quarkus.exceptions.ItemExceptionEntity;
import com.example.javaopencommerce.quarkus.exceptions.OutOfStockException;
import io.smallrye.mutiny.Uni;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
                .combinedWith((ItemEntity item, List<ItemDetailsEntity> details) -> {
                    Item itemModel = item.toItemModel();
                    itemModel.addDetails(
                        details.stream()
                            .map(ItemDetailsEntity::toItemDetailsModel)
                            .collect(Collectors.toUnmodifiableList()));
                    return itemModel;
                });

    }

    public Uni<List<Item>> getAllItems() {
        Uni<List<ItemEntity>> itemsUni = this.itemRepository.getAllItems();
        Uni<List<ItemDetailsEntity>> itemDetailsUni = this.itemRepository.getAllItemDetails();

        return combine().all()
                .unis(itemsUni, itemDetailsUni)
                .combinedWith(this::matchAndConvertToModel);
    }

    public Uni<List<Item>> getItemsListByIdList(List<Long> ids) {
        Uni<List<ItemEntity>> itemsUni = this.itemRepository.getItemsListByIdList(ids);
        Uni<List<ItemDetailsEntity>> itemDetailsUni = this.itemRepository.getItemDetailsListByIdList(ids);

        return combine().all()
                .unis(itemsUni, itemDetailsUni)
                .combinedWith(this::matchAndConvertToModel);
    }

    private List<Item> matchAndConvertToModel(List<ItemEntity> items,
        List<ItemDetailsEntity> itemDetails) {

        List<Item> itemModels = new ArrayList<>();
        for (ItemEntity item : items) {
            List<ItemDetails> itemDetailsFiltered = itemDetails.stream()
                .filter(id -> id.getItemId().equals(item.getId()))
                .map(ItemDetailsEntity::toItemDetailsModel)
                .collect(toList());

            Item itemModel = item.toItemModel();
            itemModel.addDetails(itemDetailsFiltered);
            itemModels.add(itemModel);
        }
        return itemModels;
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
                            .saveImage(ImageEntity.fromSnapshot(i.getImage().getSnapshot()))
                            .await().indefinitely();
                    return ItemEntity.fromSnapshot(i.getSnapshot());
                })
                .flatMap(this.itemRepository::saveItem);

        return savedItem
                .map(ItemEntity::toItemModel);
    }
}