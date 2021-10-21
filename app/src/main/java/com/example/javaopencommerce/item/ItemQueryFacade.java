package com.example.javaopencommerce.item;

import static java.lang.Long.parseLong;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.PageDto;
import com.example.javaopencommerce.SearchRequest;
import com.example.javaopencommerce.item.dtos.ItemDto;
import io.smallrye.mutiny.Uni;
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

  public Uni<PageDto<ItemDto>> getFilteredItems(SearchRequest request) {

    return getFilteredItemsPage(request)
        .map(itemDtoPage -> {
          sortItems(itemDtoPage.getItems(), request);
          return itemDtoPage;
        });
  }

  private void sortItems(List<ItemDto> itemDtos, SearchRequest request) {

    String sortingType = request.getSortBy().toUpperCase() + "-" + request.getOrder().toUpperCase();

    switch (sortingType) {
      case "VALUE-ASC":
        itemDtos.sort(Comparator.comparing(ItemDto::getValueGross));
        break;
      case "VALUE-DESC":
        itemDtos.sort(Comparator.comparing(ItemDto::getValueGross).reversed());
        break;
      case "NAME-DESC":
        itemDtos.sort(Comparator.comparing(ItemDto::getName).reversed());
        break;
      case "NAME-ASC":
      default:
        itemDtos.sort(Comparator.comparing(ItemDto::getName));
        break;
    }
  }

  private Uni<PageDto<ItemDto>> getFilteredItemsPage(SearchRequest request) {
    return getFilteredResults(request).flatMap(results ->
        this.queryRepository
            .getItemsListByIdList(results.getLeft())
            .map(items ->
              getItemModelPage(request.getPageNum(), request.getPageSize(),
                  results.getRight(), items)));
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
              .filter(JsonObject.class::isInstance)
              .map(JsonObject.class::cast)
              .map(o -> parseLong(o.getString("_id")))
              .collect(toList());

          return Pair.of(itemIds, totalElementsCount);
        });
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
