package com.example.database.services;

import com.example.business.models.CategoryModel;
import com.example.business.models.ItemModel;
import com.example.business.models.PageModel;
import com.example.business.models.ProducerModel;
import com.example.database.entity.Category;
import com.example.database.entity.Item;
import com.example.database.repositories.CategoryRepository;
import com.example.database.repositories.ItemRepository;
import com.example.database.repositories.ProducerRepository;
import com.example.elasticsearch.SearchService;
import com.example.utils.converters.CategoryConverter;
import com.example.utils.converters.ItemConverter;
import com.example.utils.converters.ProducerConverter;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.extern.jbosslog.JBossLog;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@JBossLog
@ApplicationScoped
public class StoreService {

    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final ProducerRepository producerRepository;
    private final SearchService searchService;



    public StoreService(CategoryRepository categoryRepository,
            ItemRepository itemRepository,
            ProducerRepository producerRepository,
            SearchService searchService) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
        this.producerRepository = producerRepository;
        this.searchService = searchService;
    }

    public List<CategoryModel> getCategoryList() {
        return categoryRepository.findAll().stream()
                .filter(c -> c.getDetails().stream()
                        .allMatch(detail -> !"shipping".equalsIgnoreCase(detail.getName())))
                .map(c -> CategoryConverter.convertToModel(c))
                .collect(Collectors.toList());
    }

    public List<ProducerModel> getProducerList() {
        return producerRepository.findAll().stream()
                .map(p -> ProducerConverter.convertToModel(p))
                .collect(Collectors.toList());
    }

    public ItemModel getItemById(Long id) {
        Item item = itemRepository.findByIdOptional(id)
                .orElseThrow(() ->
                        new WebApplicationException("Item with id " + id + " not found", Response.Status.NOT_FOUND));
        return ItemConverter
                .convertToModel(item);
    }

    public PageModel<ItemModel> getPageOfAllItems(int pageIndex, int pageSize) {
        PanacheQuery<Item> page = itemRepository.getAll(pageIndex, pageSize);
        return getItemModelPage(pageIndex, pageSize, page);
    }

    public PageModel<ItemModel> getItemsPageByCategory(Long categoryId, int pageIndex, int pageSize) {
        PanacheQuery<Item> itemPanacheQuery = itemRepository
                .listItemByCategoryId(categoryId, pageIndex, pageSize);

        return getItemModelPage(pageIndex, pageSize, itemPanacheQuery);
    }

    public PageModel<ItemModel> getItemsPageByProducer(Long producerId, int pageIndex, int pageSize) {
        PanacheQuery<Item> itemPanacheQuery = itemRepository
                .listItemByProducerId(producerId, pageIndex, pageSize);

        return getItemModelPage(pageIndex, pageSize, itemPanacheQuery);
    }

    private PageModel<ItemModel> getItemModelPage(int pageIndex, int pageSize, PanacheQuery<Item> itemPanacheQuery) {
        List<ItemModel> itemModels = itemPanacheQuery.list().stream()
                .filter(i -> validUserCategory(i.getCategory()))
                .map(i -> ItemConverter.convertToModel(i))
                .collect(Collectors.toList());

        return PageModel.<ItemModel>builder()
                .pageCount(itemPanacheQuery.pageCount())
                .pageNumber(pageIndex)
                .pageSize(pageSize)
                .totalElementsCount((int) itemPanacheQuery.count())
                .items(itemModels)
                .build();
    }

    private boolean validUserCategory(Set<Category> categories) {
        return categories.stream()
                .flatMap(category -> category.getDetails().stream())
                .allMatch(details -> !"shipping".equalsIgnoreCase(details.getName()));
    }

    //========================================================================================= PoC

    public void getFilteredResultsFromDB(JsonObject json) {
        JsonArray hits = json
                .getJsonObject("hits")
                .getJsonArray("hits");

        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < hits.size(); i++) {
            ids.add(Integer.parseInt(hits.getJsonObject(i).getString("_id")));
        }
        ids.forEach(id -> log.info("Id from elastic: " + id));
    }



}
