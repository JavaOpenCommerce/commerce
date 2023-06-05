package com.example.javaopencommerce.item;

import static java.lang.Long.parseLong;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.PageDto;
import com.example.javaopencommerce.item.dtos.ItemDto;
import io.vertx.core.json.JsonObject;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public class ItemQueryFacade {

  private final ItemSearchService searchService;
  private final ItemQueryRepository queryRepository;

  public ItemQueryFacade(ItemSearchService searchService,
      ItemQueryRepository queryRepository) {
    this.searchService = searchService;
    this.queryRepository = queryRepository;
  }

  public PageDto<ItemDto> getFilteredItems(SearchRequest request) {
    PageDto<ItemDto> filteredItemsPage = getFilteredItemsPage(request);
    sortItems(filteredItemsPage.getItems(), request);
    return filteredItemsPage;
  }

  private void sortItems(List<ItemDto> itemDtos, SearchRequest request) {

    String sortingType = request.getSortBy().toUpperCase() + "-" + request.getOrder().toUpperCase();
    switch (sortingType) {
      case "VALUE-ASC" -> itemDtos.sort(Comparator.comparing(ItemDto::getValueGross));
      case "VALUE-DESC" -> itemDtos.sort(Comparator.comparing(ItemDto::getValueGross).reversed());
      case "NAME-DESC" -> itemDtos.sort(Comparator.comparing(ItemDto::getName).reversed());
      default -> itemDtos.sort(Comparator.comparing(ItemDto::getName));
    }
  }

  private PageDto<ItemDto> getFilteredItemsPage(SearchRequest request) {
    Pair<List<Long>, Integer> filteredResults = getFilteredResults(request);

    List<ItemDto> itemsFound = this.queryRepository
        .getItemsByIdList(filteredResults.getLeft());

    return getItemModelPage(request.getPageNum(), request.getPageSize(),
        filteredResults.getRight(), itemsFound);
  }

  private Pair<List<Long>, Integer> getFilteredResults(SearchRequest request) {
    JsonObject json = this.searchService.searchItemsBySearchRequest(request);

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
        .filter(JsonObject.class::isInstance)
        .map(JsonObject.class::cast)
        .map(o -> parseLong(o.getString("_id")))
        .collect(toList());

    return Pair.of(itemIds, totalElementsCount);
  }

  private PageDto<ItemDto> getItemModelPage(int pageIndex, int pageSize, int totalCount,
      List<ItemDto> items) {
    int pageCount = Double.valueOf(Math.ceil(totalCount / (double) pageSize)).intValue();

    return PageDto.<ItemDto>builder()
        .pageCount(pageCount)
        .pageNumber(pageIndex)
        .pageSize(pageSize)
        .totalElementsCount(totalCount)
        .items(items)
        .build();
  }
}
