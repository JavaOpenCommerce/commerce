package com.example.javaopencommerce.quarkus.app;

import com.example.javaopencommerce.PageDto;
import com.example.javaopencommerce.category.CategoryDto;
import com.example.javaopencommerce.elasticsearch.SearchRequest;
import com.example.javaopencommerce.item.ItemDetailDto;
import com.example.javaopencommerce.item.ItemDto;
import com.example.javaopencommerce.producer.ProducerDto;
import com.example.javaopencommerce.rest.services.StoreDtoService;
import io.smallrye.mutiny.Uni;
import java.util.List;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
        return this.storeService.getItemById(id);
    }

    @GET
    @Path("/items")
    public Uni<PageDto<ItemDto>> search(@BeanParam SearchRequest request) {
        return this.storeService.getFilteredItems(request);
    }

    @GET
    @Path("/categories")
    public Uni<List<CategoryDto>> getAllCategories() {
        return this.storeService.getAllCategories();
    }

    @GET
    @Path("/producers")
    public Uni<List<ProducerDto>> getAllProducers() {
        return this.storeService.getAllProducers();
    }

}
