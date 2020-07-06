package com.example.database.services;

import com.example.database.entity.Category;
import com.example.database.entity.Item;
import com.example.database.entity.ItemDetails;
import com.example.database.entity.Producer;
import com.example.database.repositories.interfaces.CategoryRepository;
import com.example.database.repositories.interfaces.ItemRepository;
import com.example.database.repositories.interfaces.ProducerRepository;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ItemAssemblingService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final ProducerRepository producerRepository;


    public ItemAssemblingService(ItemRepository itemRepository,
            CategoryRepository categoryRepository, ProducerRepository producerRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.producerRepository = producerRepository;
    }

    public Uni<Item> assembleSingleItem(Long id) {

        Uni<Item> itemUni = itemRepository.getItemById(id);
        Uni<List<ItemDetails>> itemDetailsUni = itemRepository.getItemDetailsListByItemId(id);
        Uni<List<Category>> categoriesUni = categoryRepository.getCategoriesByItemId(id);
        Uni<Producer> producerUni = producerRepository.getProducerByItemId(id);

        return Uni.combine().all()
                .unis(itemUni, itemDetailsUni, categoriesUni, producerUni)
                .combinedWith((item, itemDetails, categories, producer) -> {
                    item.setDetails(itemDetails);
                    item.setCategory(categories);
                    item.setProducer(producer);
                    return item;
                });
    }

    public Uni<List<Item>> assemblyFullItemList() {
        Uni<List<Item>> items = itemRepository.getAll();
        Uni<List<Category>> categories = categoryRepository.getAll();
        //cdn...
        return items;
    }
}
