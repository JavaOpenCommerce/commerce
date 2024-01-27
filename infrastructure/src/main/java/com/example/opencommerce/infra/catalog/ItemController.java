package com.example.opencommerce.infra.catalog;

import com.example.opencommerce.app.PageDto;
import com.example.opencommerce.app.PageDto.CreatePageCommand;
import com.example.opencommerce.app.catalog.query.FullItemDto;
import com.example.opencommerce.app.catalog.query.ItemDto;
import com.example.opencommerce.app.catalog.query.ItemFamilyMemberDto;
import com.example.opencommerce.app.catalog.query.ItemFamilyQueryRepository;
import com.example.opencommerce.app.catalog.query.ItemQueryFacade;
import com.example.opencommerce.app.catalog.query.SearchRequest;
import com.example.opencommerce.app.pricing.query.PriceDto;
import com.example.opencommerce.app.pricing.query.PriceQueryRepository;
import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.infra.catalog.views.BaseItemView;
import com.example.opencommerce.infra.catalog.views.FullItemView;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Map;

import static com.example.opencommerce.app.catalog.query.ItemQueryFacade.SearchResult;

@Path("items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemController {

    private final ItemFamilyQueryRepository familyRepository;
    private final ItemQueryFacade queryFacade;
    private final PriceQueryRepository priceQueryRepository;
    private final ItemViewMapper mapper;
    private final ItemSorter sorter;

    ItemController(ItemFamilyQueryRepository familyRepository,
                   ItemQueryFacade itemQueryFacade,
                   PriceQueryRepository priceQueryRepository,
                   ItemViewMapper mapper, ItemSorter sorter) {
        this.familyRepository = familyRepository;
        this.queryFacade = itemQueryFacade;
        this.priceQueryRepository = priceQueryRepository;
        this.mapper = mapper;
        this.sorter = sorter;
    }

    @GET
    @Path("/{id}")
    public FullItemView getItemById(@PathParam("id") Long id) {
        ItemId itemId = ItemId.of(id);
        FullItemDto itemDto = this.queryFacade.getItemById(itemId);
        PriceDto priceDto = this.priceQueryRepository.getPriceByItemId(itemId);
        return this.mapper.toFullItemView(itemDto, priceDto);
    }

    @GET
    @Path("/{id}/family")
    public List<ItemFamilyMemberDto> getItemFamilyByItsId(@PathParam("id") Long id) {
        return this.familyRepository.findItemFamily(id);
    }

    @POST
    public PageDto<BaseItemView> search(SearchRequest request) {
        SearchResult result = this.queryFacade.getFilteredItems(request);

        List<ItemDto> foundItems = result.items();
        Map<ItemId, PriceDto> itemPrices = this.priceQueryRepository.getPricesForItemsWithIds(foundItems.stream()
                .map(ItemDto::getId)
                .map(ItemId::of)
                .toList());

        List<BaseItemView> items = foundItems.stream()
                .map(item -> this.mapper.toBaseItemView(item, itemPrices.get(ItemId.of(item.getId()))))
                .toList();

        items = sorter.sortItems(items, request);

        CreatePageCommand<BaseItemView> command =
                new CreatePageCommand<>(items, result.total(), request.getPageNum(), request.getPageSize());

        return PageDto.fromCommand(command);
    }
}
