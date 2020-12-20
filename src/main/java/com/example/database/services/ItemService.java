package com.example.database.services;

import com.example.business.models.ItemModel;
import com.example.database.entity.Category;
import com.example.database.entity.Item;
import com.example.database.entity.ItemDetails;
import com.example.database.entity.Producer;
import com.example.database.repositories.interfaces.CategoryRepository;
import com.example.database.repositories.interfaces.ImageRepository;
import com.example.database.repositories.interfaces.ItemRepository;
import com.example.database.repositories.interfaces.ProducerRepository;
import com.example.quarkus.exceptions.ItemExceptionEntity;
import com.example.quarkus.exceptions.OutOfStockException;
import com.example.utils.converters.ImageConverter;
import com.example.utils.converters.ItemConverter;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

import static io.smallrye.mutiny.Uni.combine;
import static java.util.Collections.emptyList;

@ApplicationScoped
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final ProducerRepository producerRepository;
    private final ImageRepository imageRepository;


    public ItemService(ItemRepository itemRepository,
                       CategoryRepository categoryRepository, ProducerRepository producerRepository, ImageRepository imageRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.producerRepository = producerRepository;
        this.imageRepository = imageRepository;
    }

    public Uni<ItemModel> getItemById(Long id) {
        Uni<Item> itemUni = itemRepository.getItemById(id);
        Uni<List<ItemDetails>> itemDetailsUni = itemRepository.getItemDetailsListByItemId(id);
        Uni<List<Category>> categoriesUni = categoryRepository.getCategoriesByItemId(id);
        Uni<Producer> producerUni = producerRepository.getProducerByItemId(id);

        return combine().all()
                .unis(itemUni, itemDetailsUni, categoriesUni, producerUni)
                .combinedWith(ItemConverter::convertToModel);
    }

    public Uni<List<ItemModel>> getAllItems() {
        Uni<List<Item>> itemsUni = itemRepository.getAllItems();
        Uni<List<Category>> categoriesUni = categoryRepository.getAll();
        Uni<List<ItemDetails>> itemDetailsUni = itemRepository.getAllItemDetails();
        Uni<List<Producer>> producersUni = producerRepository.getAll();

        return combine().all()
                .unis(itemsUni, itemDetailsUni, categoriesUni, producersUni)
                .combinedWith(
                        ItemConverter::convertToItemModelList);
    }

    public Uni<List<ItemModel>> getItemsListByIdList(List<Long> ids) {
        Uni<List<Item>> itemsUni = itemRepository.getItemsListByIdList(ids);
        Uni<List<ItemDetails>> itemDetailsUni = itemRepository.getItemDetailsListByIdList(ids);
        Uni<List<Category>> categoriesUni = categoryRepository.getCategoriesListByIdList(ids);
        Uni<List<Producer>> producersUni = producerRepository.getProducersListByIdList(ids);

        return combine().all()
                .unis(itemsUni, itemDetailsUni, categoriesUni, producersUni)
                .combinedWith(ItemConverter::convertToItemModelList);
    }

    public Uni<Integer> changeStock(Long id, int amount) {
        Uni<Integer> itemStock = itemRepository.getItemStock(id);
        return itemStock.flatMap(stock -> {
            if (stock == -1) {
                throw new OutOfStockException(
                        ItemExceptionEntity.create(id, "Item is out of stock! Rollback"));
            } else if (stock < amount) {
                throw new OutOfStockException(
                        ItemExceptionEntity.create(id, "Not enough items in stock! Rollback"));
            } else {
                return itemRepository.changeItemStock(id, stock - amount);
            }
        });
    }

    @Transactional
    public Uni<ItemModel> addNewItem(Uni<ItemModel> item) {
        Uni<Item> savedItem = item
                .map(i -> {
                    imageRepository
                            .saveImage(ImageConverter.convertModelToEntity(i.getImage()))
                            .await().indefinitely();
                    return ItemConverter.convertModelToEntity(i);
                })
                .flatMap(itemRepository::saveItem);

        return savedItem
                .map(i -> ItemConverter.convertToModel(i, emptyList(), emptyList(), null));
    }
}