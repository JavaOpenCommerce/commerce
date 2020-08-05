package com.example.database.services;

import com.example.business.models.CategoryModel;
import com.example.business.models.ItemModel;
import com.example.business.models.PageModel;
import com.example.business.models.ProducerModel;
import com.example.database.repositories.interfaces.CategoryRepository;
import com.example.database.repositories.interfaces.ProducerRepository;
import com.example.elasticsearch.SearchRequest;
import com.example.elasticsearch.SearchService;
import com.example.utils.converters.CategoryConverter;
import com.example.utils.converters.ProducerConverter;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import lombok.extern.jbosslog.JBossLog;
import org.apache.commons.lang3.tuple.Pair;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

import static java.lang.Long.parseLong;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;


@JBossLog
@ApplicationScoped
public class StoreService {

    private final ItemService itemService;
    private final SearchService searchService;
    private final CategoryRepository categoryRepository;
    private final ProducerRepository producerRepository;


    public StoreService(ItemService itemService,
            SearchService searchService,
            CategoryRepository categoryRepository,
            ProducerRepository producerRepository) {
        this.itemService = itemService;
        this.searchService = searchService;
        this.categoryRepository = categoryRepository;
        this.producerRepository = producerRepository;
    }

    public Uni<ItemModel> getItemById(Long id) {
        return itemService.getItemById(id);
    }

    public Uni<PageModel<ItemModel>> getFilteredItemsPage(SearchRequest request) {
        return getFilteredResults(request).onItem().produceUni(results ->
            itemService
                    .getItemsListByIdList(results.getLeft())
                    .onItem()
                    .apply(items -> getItemModelPage(request.getPageNum(), request.getPageSize(), results.getRight(), items)));
    }

    public Uni<List<CategoryModel>> getAllCategories() {
        return categoryRepository.getAll().onItem().apply(categories ->
            categories.stream()
                    .filter(cat -> cat.getDetails().stream()
                            .allMatch(detail -> !"shipping".equalsIgnoreCase(detail.getName())))
                    .map(cat -> CategoryConverter.convertToModel(cat))
                    .collect(toList()));
    }

    public Uni<List<ProducerModel>> getAllProducers() {
        return producerRepository.getAll().onItem().apply(producers ->
                producers.stream()
                        .map(prod -> ProducerConverter.convertToModel(prod))
                        .collect(toList()));
    }

    private Uni<Pair<List<Long>, Integer>> getFilteredResults(SearchRequest request) {

        return searchService
                .searchItemsBySearchRequest(request).onItem().apply(json -> {

                    //null check on json
                    if (json == null || json.isEmpty() || json.getJsonObject("hits") == null
                            || json.getJsonObject("hits").getJsonArray("hits") == null) {
                        return Pair.of(emptyList(), 0);
                    }
                    //total elements count found by elasticsearch query
                    int totalElementsCount = json
                            .getJsonObject("hits")
                            .getJsonObject("total")
                            .getInteger("value");

                    //list of item id's returned by a query restricted to a specific size and page number
                    List<Long> itemIds = json
                            .getJsonObject("hits")
                            .getJsonArray("hits")
                            .stream()
                            .filter(o -> o instanceof JsonObject)
                            .map(JsonObject.class::cast)
                            .map(o -> parseLong(o.getString("_id")))
                            .collect(toList());

                    return Pair.of(itemIds, totalElementsCount);
                });
    }

    private PageModel<ItemModel> getItemModelPage(int pageIndex, int pageSize, int totalCount, List<ItemModel> items) {

        int pageCount = (int) Math.ceil((double) totalCount / pageSize);

        return PageModel.<ItemModel>builder()
                .pageCount(pageCount)
                .pageNumber(pageIndex)
                .pageSize(pageSize)
                .totalElementsCount(totalCount)
                .items(items)
                .build();
    }
}




