package com.example.javaopencommerce.catalog;

import com.example.javaopencommerce.ItemId;
import com.example.javaopencommerce.PageDto;
import com.example.javaopencommerce.catalog.dtos.FullItemDto;
import com.example.javaopencommerce.catalog.dtos.ItemDto;
import com.example.javaopencommerce.catalog.dtos.ItemFamilyMemberDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemController {

    private final ItemQueryRepository itemRepository;
    private final ItemFamilyQueryRepository familyRepository;
    private final ItemQueryFacade queryFacade;

    ItemController(ItemQueryRepository itemRepository,
                   ItemFamilyQueryRepository familyRepository,
                   ItemQueryFacade queryFacade) {
        this.itemRepository = itemRepository;
        this.familyRepository = familyRepository;
        this.queryFacade = queryFacade;
    }

    @GET
    @Path("/{id}")
    public FullItemDto getItemById(@PathParam("id") Long id) {
        ItemId itemId = ItemId.of(id);
        return this.itemRepository.getItemById(itemId);
    }

    @GET
    @Path("/{id}/family")
    public List<ItemFamilyMemberDto> getItemFamilyByItsId(@PathParam("id") Long id) {
        return this.familyRepository.findItemFamily(id);
    }

    @POST
    public PageDto<ItemDto> search(SearchRequest request) {
        return this.queryFacade.getFilteredItems(request);
    }
}
