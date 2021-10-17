package com.example.javaopencommerce.item;

import com.example.javaopencommerce.PageDto;
import com.example.javaopencommerce.SearchRequest;
import com.example.javaopencommerce.item.dtos.ItemDetailsDto;
import com.example.javaopencommerce.item.dtos.ItemDto;
import io.smallrye.mutiny.Uni;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemController {

    private final ItemQueryRepository queryRepository;
    private final ItemQueryFacade queryFacade;

    ItemController(ItemQueryRepository queryRepository,
        ItemQueryFacade queryFacade) {
        this.queryRepository = queryRepository;
        this.queryFacade = queryFacade;
    }

    @GET
    @Path("/{id}")
    public Uni<ItemDetailsDto> getItemById(@PathParam("id") Long id) {
        return this.queryRepository.getItemById(id);
    }

    @GET
    public Uni<PageDto<ItemDto>> search(@BeanParam SearchRequest request) {
        return this.queryFacade.getFilteredItems(request);
    }
}
