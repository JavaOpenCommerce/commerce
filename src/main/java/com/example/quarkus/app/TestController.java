package com.example.quarkus.app;

import com.example.database.entity.Image;
import com.example.database.entity.Item;
import com.example.database.repositories.interfaces.ItemRepository;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TestController {

    private final ItemRepository itemRepository;

    public TestController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GET
    @Path("/1")
    public Uni<Item> test1() {
        Item item = Item.builder()
                .stock(3)
                .producerId(2L)
                .valueGross(BigDecimal.ONE)
                .vat(0.23)
                .image(Image.builder().id(2L).build())
                .build();
        return itemRepository.saveItem(item);
    }
}
