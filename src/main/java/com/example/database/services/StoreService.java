package com.example.database.services;

import com.example.business.models.CategoryModel;
import com.example.business.models.ItemModel;
import com.example.business.models.PageModel;
import com.example.database.entity.Item;
import com.example.database.repositories.CategoryRepository;
import com.example.database.repositories.ItemRepository;
import com.example.utils.converters.CategoryConverter;
import com.example.utils.converters.ItemConverter;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class StoreService {

    private final CategoryRepository categoryRepository;
    private final ItemRepository repository;


    public StoreService(CategoryRepository categoryRepository, ItemRepository repository) {
        this.categoryRepository = categoryRepository;
        this.repository = repository;
    }

    public List<CategoryModel> getCategoryList() {
        return categoryRepository.findAll().stream()
                .map(c -> CategoryConverter.convertToModel(c))
                .filter(cm -> !"Shipping".equals(cm.getCategoryName()))
                .collect(Collectors.toList());
    }

    public ItemModel getItemModel(Long id) {
        Item item = repository.findByIdOptional(id)
                .orElseThrow(() ->
                        new WebApplicationException("Item with id " + id + " not found", Response.Status.NOT_FOUND));
        return ItemConverter
                .convertToModel(item);
    }

    public PageModel<ItemModel> getItemsPageByCategory(Long categoryId, int pageIndex, int pageSize) {
        PanacheQuery<Item> itemPanacheQuery = repository
                .listItemByCategoryId(categoryId, pageIndex, pageSize);

        return getItemModelPage(pageIndex, pageSize, itemPanacheQuery);
    }

    public PageModel<ItemModel> getItemsPageByProducer(Long producerId, int pageIndex, int pageSize) {
        PanacheQuery<Item> itemPanacheQuery = repository
                .listItemByProducerId(producerId, pageIndex, pageSize);

        return getItemModelPage(pageIndex, pageSize, itemPanacheQuery);
    }

    private PageModel<ItemModel> getItemModelPage(int pageIndex, int pageSize, PanacheQuery<Item> itemPanacheQuery) {
        List<ItemModel> itemModels = itemPanacheQuery.list().stream()
                .map(i -> ItemConverter.convertToModel(i)).
                        collect(Collectors.toList());

        return PageModel.<ItemModel>builder()
                .pageCount(itemPanacheQuery.pageCount())
                .pageNumber(pageIndex)
                .pageSize(pageSize)
                .totalElementsCount((int) itemPanacheQuery.count())
                .items(itemModels)
                .build();
    }

}
