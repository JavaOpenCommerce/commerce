package com.example.opencommerce.app.catalog.query;

import com.example.opencommerce.domain.ItemId;
import io.vertx.core.json.JsonObject;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

import static java.lang.Long.parseLong;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class ItemQueryFacade {

    private final ItemSearchService searchService;
    private final ItemQueryRepository queryRepository;

    public ItemQueryFacade(ItemSearchService searchService,
                           ItemQueryRepository queryRepository) {
        this.searchService = searchService;
        this.queryRepository = queryRepository;
    }

    public FullItemDto getItemById(ItemId itemId) {
        return this.queryRepository.getItemById(itemId);
    }

    public SearchResult getFilteredItems(SearchRequest request) {
        Pair<List<Long>, Integer> filteredResults = getFilteredResults(request);

        List<ItemId> itemIds = filteredResults.getLeft()
                .stream()
                .map(ItemId::of)
                .toList();

        List<ItemDto> productsFound = this.queryRepository
                .getItemsByIdList(itemIds);

        return new SearchResult(productsFound, filteredResults.getRight());
    }

    private Pair<List<Long>, Integer> getFilteredResults(SearchRequest request) {
        JsonObject json = this.searchService.searchProductsBySearchRequest(request);

        //null check on json
        if (json == null || json.isEmpty() || json.getJsonObject("hits") == null
                || json.getJsonObject("hits")
                .getJsonArray("hits") == null) {
            return Pair.of(emptyList(), 0);
        }
        //total elements count found by elasticsearch query
        int totalElementsCount = json
                .getJsonObject("hits")
                .getJsonObject("total")
                .getInteger("value");

        //list of product id's returned by a query restricted to a specific size and page number
        List<Long> productIds = json
                .getJsonObject("hits")
                .getJsonArray("hits")
                .stream()
                .filter(JsonObject.class::isInstance)
                .map(JsonObject.class::cast)
                .map(o -> parseLong(o.getString("_id")))
                .collect(toList());

        return Pair.of(productIds, totalElementsCount);
    }

    public record SearchResult(List<ItemDto> items, int total) {}
}
