package com.example.javaopencommerce.item;

import com.example.javaopencommerce.PageDto;
import com.example.javaopencommerce.SearchRequest;
import io.smallrye.mutiny.Uni;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ItemController {

    private final ItemFacade storeService;

    ItemController(ItemFacade storeService) {
        this.storeService = storeService;
    }


    @GET
    @Path("/items/{id}")
    public Uni<ItemDetailsDto> getItemById(@PathParam("id") Long id) {
        return this.storeService.getItemById(id);
    }

    @GET
    @Path("/items")
    public Uni<PageDto<ItemDto>> search(@BeanParam SearchRequest request) {
        return this.storeService.getFilteredItems(request);
    }
}
