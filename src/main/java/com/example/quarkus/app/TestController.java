package com.example.quarkus.app;

import com.example.database.entity.Image;
import com.example.database.entity.Item;
import com.example.database.entity.OrderDetails;
import com.example.database.repositories.interfaces.ItemRepository;
import com.example.database.repositories.interfaces.OrderDetailsRepository;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TestController {

    private final ItemRepository itemRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    public TestController(ItemRepository itemRepository, OrderDetailsRepository orderDetailsRepository) {
        this.itemRepository = itemRepository;
        this.orderDetailsRepository = orderDetailsRepository;
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

    @POST
    @Path("/2")
    public Uni<OrderDetails> test2() {
        OrderDetails order = OrderDetails.builder()
                .shippingAddressId(1L)
                .userEntityId(1L)
                .build();

        return orderDetailsRepository.saveOrder(order);
    }

    @GET
    @Path("/3")
    public Uni<OrderDetails> test3() {
        return orderDetailsRepository.findOrderDetailsById(3L);
    }
}
