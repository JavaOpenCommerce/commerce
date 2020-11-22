package com.example.database.services;

import com.example.business.models.ItemModel;
import com.example.database.entity.Category;
import com.example.database.entity.Item;
import com.example.database.entity.ItemDetails;
import com.example.database.entity.Producer;
import com.example.database.repositories.interfaces.CategoryRepository;
import com.example.database.repositories.interfaces.ItemRepository;
import com.example.database.repositories.interfaces.ProducerRepository;
import com.example.utils.converters.ItemConverter;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

import static io.smallrye.mutiny.Uni.combine;

@ApplicationScoped
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final ProducerRepository producerRepository;


    public ItemService(ItemRepository itemRepository,
            CategoryRepository categoryRepository, ProducerRepository producerRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.producerRepository = producerRepository;
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
}
