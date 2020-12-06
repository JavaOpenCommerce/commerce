package com.example.quarkus.app;

import com.example.database.entity.Image;
import com.example.database.entity.Item;
import com.example.database.repositories.interfaces.ItemRepository;
import com.example.rest.dtos.OrderDetailsDto;
import com.example.rest.services.OrderDetailsDtoService;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TestController {

    private final ItemRepository itemRepository;
    private final OrderDetailsDtoService orderDetailsDtoService;

    public TestController(ItemRepository itemRepository, OrderDetailsDtoService orderDetailsDtoService) {
        this.itemRepository = itemRepository;
        this.orderDetailsDtoService = orderDetailsDtoService;
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

    @GET
    @Path("/get_order")
    public Uni<OrderDetailsDto> getOrderDetailsById(@QueryParam("id") Long id) {
        return orderDetailsDtoService.getOrderDetailsById(id);
    }

    @POST
    @Path("/save_order")
    public Uni<OrderDetailsDto> testSaveOrder(OrderDetailsDto orderDetails) {
        return orderDetailsDtoService.saveOrderDetails(Uni.createFrom().item(orderDetails));
    }
}
