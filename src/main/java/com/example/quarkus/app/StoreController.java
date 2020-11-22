package com.example.quarkus.app;

import com.example.elasticsearch.SearchRequest;
import com.example.rest.dtos.*;
import com.example.rest.services.StoreDtoService;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/store")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StoreController {

    private final StoreDtoService storeService;

    public StoreController(StoreDtoService storeService) {
        this.storeService = storeService;
    }


    @GET
    @Path("/items/{id}")
    public Uni<ItemDetailDto> getItemById(@PathParam("id") Long id) {
        return storeService.getItemById(id);
    }

    @GET
    @Path("/items")
    public Uni<PageDto<ItemDto>> search(@BeanParam SearchRequest request) {
        return storeService.getFilteredItems(request);
    }

    @GET
    @Path("/categories")
    public Uni<List<CategoryDto>> getAllCategories() {
        return storeService.getAllCategories();
    }

    @GET
    @Path("/producers")
    public Uni<List<ProducerDto>> getAllProducers() {
        return storeService.getAllProducers();
    }

}
