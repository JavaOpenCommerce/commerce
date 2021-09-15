package com.example.javaopencommerce.database.services;

import static java.lang.Long.parseLong;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.Page;
import com.example.javaopencommerce.category.Category;
import com.example.javaopencommerce.category.CategoryRepository;
import com.example.javaopencommerce.elasticsearch.SearchRequest;
import com.example.javaopencommerce.elasticsearch.SearchService;
import com.example.javaopencommerce.item.Item;
import com.example.javaopencommerce.producer.Producer;
import com.example.javaopencommerce.producer.ProducerRepository;
import com.example.javaopencommerce.utils.converters.CategoryConverter;
import com.example.javaopencommerce.utils.converters.ProducerConverter;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.tuple.Pair;

@Log4j2
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

    public Uni<Item> getItemById(Long id) {
        return this.itemService.getItemById(id);
    }

    public Uni<Page<Item>> getFilteredItemsPage(SearchRequest request) {
        return getFilteredResults(request).flatMap(results ->
                this.itemService
                        .getItemsListByIdList(results.getLeft())
                        .map(items -> {
                            List<Item> filteredList = items.stream()
                                    //.filter(i -> isValidCategory(i.getCategory()))// TODO!!
                                    .collect(toList());

                            //list size difference after filtering out Shipping methods
                            int difference = items.size() - filteredList.size();
                            return getItemModelPage(request.getPageNum(), request.getPageSize(),
                                    results.getRight() - difference, filteredList);
                        })
        );
    }

    public Uni<List<Category>> getAllCategories() {
        return this.categoryRepository.getAll().map(categories ->
                categories.stream()
                        .filter(cat -> cat.getDetails().stream()
                                .noneMatch(detail -> "shipping".equalsIgnoreCase(detail.getName())))
                        .map(CategoryConverter::convertToModel)
                        .collect(toList()));
    }

    public Uni<List<Producer>> getAllProducers() {
        return this.producerRepository.getAll().map(producers ->
                producers.stream()
                        .map(ProducerConverter::convertToModel)
                        .collect(toList()));
    }

    private Uni<Pair<List<Long>, Integer>> getFilteredResults(SearchRequest request) {

        return this.searchService
                .searchItemsBySearchRequest(request).map(json -> {

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

    private Page<Item> getItemModelPage(int pageIndex, int pageSize, int totalCount, List<Item> items) {

        int pageCount = (int) Math.ceil((double) totalCount / pageSize);

        return Page.<Item>builder()
                .pageCount(pageCount)
                .pageNumber(pageIndex)
                .pageSize(pageSize)
                .totalElementsCount(totalCount)
                .items(items)
                .build();
    }

    private boolean isValidCategory(List<Category> categories) {
        return categories.stream()
                .flatMap(category -> category.getDetails().stream())
                .noneMatch(details -> "shipping".equalsIgnoreCase(details.getName()));
    }
}




