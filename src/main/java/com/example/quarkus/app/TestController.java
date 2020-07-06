package com.example.quarkus.app;

import com.example.database.entity.Item;
import com.example.database.services.ItemAssemblingService;
import com.example.rest.dtos.CategoryDto;
import com.example.rest.dtos.ItemDetailDto;
import com.example.rest.dtos.ItemDto;
import com.example.rest.dtos.PageDto;
import com.example.rest.dtos.ProducerDto;
import com.example.rest.services.CardDtoService;
import com.example.rest.services.StoreDtoService;
import com.example.utils.LanguageResolver;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TestController {

    private final StoreDtoService storeService;
    private final CardDtoService cardService;
    private final LanguageResolver extractor;
    private final ItemAssemblingService itemAssemblingService;

    public TestController(StoreDtoService storeService,
            CardDtoService cardService,
            LanguageResolver extractor, ItemAssemblingService itemAssemblingService) {
        this.storeService = storeService;
        this.cardService = cardService;
        this.extractor = extractor;
        this.itemAssemblingService = itemAssemblingService;
    }


    @GET
    @Path("reactive/items/{id}")
    public Uni<ItemDetailDto> getItemById(@PathParam("id") Long id) {
        return storeService.getItemById(id);
    }

    @GET
    @Path("/items/category/{categoryId}")
    public PageDto getAllByCategory(@PathParam("categoryId") Long id,
            @QueryParam("page") int page,
            @QueryParam("size") int size) {
        return storeService.getItemsPageByCategory(id, page, size);
    }

    @GET
    @Path("/items/producer/{producerId}")
    public PageDto getAllByProducer(@PathParam("producerId") Long id,
            @QueryParam("page") Optional<Integer> page,
            @QueryParam("size") Optional<Integer> size) {
        return storeService.getItemsPageByProducer(id, page.orElse(0), size.orElse(10));
    }

    @GET
    @Path("/items")
    public PageDto getAll(@QueryParam("page") Optional<Integer> page,
            @QueryParam("size") Optional<Integer> size) {
        return storeService.getPageOfAllItems(page.orElse(0), size.orElse(10));
    }

    @GET
    @Path("/categories")
    public List<CategoryDto> getAllCategories() {
        return storeService.getCategoryList();
    }

    @GET
    @Path("/producers")
    public List<ProducerDto> getAllProducers() {
        return storeService.getProducerList();
    }

    @GET
    @Path("/shipping")
    public List<ItemDto> getAllShippingMethods() {
        return cardService.getShippingMethods();
    }

    @GET
    @Path("/locale")
    public String getLocale() {
        return extractor.getLanguage();
    }

    //=============================================================== TEST

    @GET
    @Path("reactive/items")
    public Uni<List<Item>> getAll() {
        return itemAssemblingService.assemblyFullItemList();
    }

}
