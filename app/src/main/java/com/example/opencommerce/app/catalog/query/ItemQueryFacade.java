package com.example.opencommerce.app.catalog.query;

import com.example.opencommerce.app.PageDto;
import com.example.opencommerce.domain.ItemId;
import io.vertx.core.json.JsonObject;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
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

    public PageDto<ItemDto> getFilteredItems(SearchRequest request) {
        Pair<List<Long>, Integer> filteredResults = getFilteredResults(request);

        List<ItemId> itemIds = filteredResults.getLeft()
                .stream()
                .map(ItemId::of)
                .toList();

        List<ItemDto> productsFound = this.queryRepository
                .getItemsByIdList(itemIds);

        List<ItemDto> sortedProducts = sortItems(productsFound, request);

        return getItemModelPage(request.getPageNum(), request.getPageSize(),
                filteredResults.getRight(), sortedProducts);
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

    private List<ItemDto> sortItems(List<ItemDto> productDtos, SearchRequest request) {
        String sortingType = request.getSortBy()
                .toUpperCase() + "-" + request.getOrder()
                .toUpperCase();
        return productDtos.stream()
                .sorted(pickItemComparator(sortingType))
                .toList();
    }

    private Comparator<ItemDto> pickItemComparator(String sortingType) {
        return switch (sortingType) {
            case "VALUE-ASC" -> Comparator.comparing(ItemDto::getValueGross);
            case "VALUE-DESC" -> Comparator.comparing(ItemDto::getValueGross)
                    .reversed();
            case "NAME-DESC" -> Comparator.comparing(ItemDto::getName)
                    .reversed();
            default -> Comparator.comparing(ItemDto::getName);
        };
    }

    private PageDto<ItemDto> getItemModelPage(int pageIndex, int pageSize, int totalCount,
                                              List<ItemDto> products) {
        int pageCount = Double.valueOf(Math.ceil(totalCount / (double) pageSize))
                .intValue();

        return PageDto.<ItemDto>builder()
                .pageCount(pageCount)
                .pageNumber(pageIndex)
                .pageSize(pageSize)
                .totalElementsCount(totalCount)
                .items(products)
                .build();
    }
}
