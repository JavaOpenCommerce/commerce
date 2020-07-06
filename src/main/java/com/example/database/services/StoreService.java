package com.example.database.services;

import com.example.business.models.CategoryModel;
import com.example.business.models.ItemModel;
import com.example.business.models.PageModel;
import com.example.business.models.ProducerModel;
import com.example.database.entity.Category;
import com.example.database.entity.Item;
import com.example.database.entity.Producer;
import com.example.database.repositories.interfaces.ItemRepository;
import com.example.elasticsearch.SearchService;
import com.example.utils.converters.CategoryConverter;
import com.example.utils.converters.ItemConverter;
import com.example.utils.converters.ProducerConverter;
import io.smallrye.mutiny.Uni;
import lombok.extern.jbosslog.JBossLog;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JBossLog
@ApplicationScoped
public class StoreService {

    private final ItemRepository itemRepository;
    private final ItemAssemblingService itemAssemblingService;
    private final SearchService searchService;



    public StoreService(ItemRepository itemRepository,
            ItemAssemblingService itemAssemblingService, SearchService searchService) {
        this.itemRepository = itemRepository;
        this.itemAssemblingService = itemAssemblingService;
        this.searchService = searchService;
    }

    public Uni<ItemModel> getItemById(Long id) {
        Uni<Item> itemUni = itemAssemblingService.assembleSingleItem(id);

        return itemUni.onItem().apply(item -> ItemConverter.convertToModel(item));
    }

    public List<CategoryModel> getCategoryList() {
        return new ArrayList<Category>().stream() //TODO
                .filter(c -> c.getDetails().stream()
                        .allMatch(detail -> !"shipping".equalsIgnoreCase(detail.getName())))
                .map(c -> CategoryConverter.convertToModel(c))
                .collect(Collectors.toList());
    }

    public List<ProducerModel> getProducerList() {
        return new ArrayList<Producer>().stream() //TODO
                .map(p -> ProducerConverter.convertToModel(p))
                .collect(Collectors.toList());
    }

    public PageModel<ItemModel> getPageOfAllItems(int pageIndex, int pageSize) {
        List<Item> page = itemRepository.getAll(pageIndex, pageSize);
        return getItemModelPage(pageIndex, pageSize, page);
    }

    public PageModel<ItemModel> getItemsPageByCategory(Long categoryId, int pageIndex, int pageSize) {
        List<Item> itemPanacheQuery = itemRepository
                .listItemByCategoryId(categoryId, pageIndex, pageSize);

        return getItemModelPage(pageIndex, pageSize, itemPanacheQuery);
    }

    public PageModel<ItemModel> getItemsPageByProducer(Long producerId, int pageIndex, int pageSize) {
        List<Item> itemPanacheQuery = itemRepository
                .listItemByProducerId(producerId, pageIndex, pageSize);

        return getItemModelPage(pageIndex, pageSize, itemPanacheQuery);
    }

    private PageModel<ItemModel> getItemModelPage(int pageIndex, int pageSize, List<Item> itemPanacheQuery) {
        List<ItemModel> itemModels = itemPanacheQuery.stream() //TODO
                .filter(i -> validUserCategory(i.getCategory()))
                .map(i -> ItemConverter.convertToModel(i))
                .collect(Collectors.toList());

        return PageModel.<ItemModel>builder()
                .pageCount(0) //TODO
                .pageNumber(pageIndex)
                .pageSize(pageSize)
                .totalElementsCount(0) //TODO
                .items(itemModels)
                .build();
    }

    private boolean validUserCategory(List<Category> categories) {
        return categories.stream()
                .flatMap(category -> category.getDetails().stream())
                .allMatch(details -> !"shipping".equalsIgnoreCase(details.getName()));
    }
}
