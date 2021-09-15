package com.example.javaopencommerce.quarkus.app;

import com.example.javaopencommerce.image.ImageEntity;
import com.example.javaopencommerce.item.ItemEntity;
import com.example.javaopencommerce.item.ItemRepository;
import com.example.javaopencommerce.order.OrderDetailsDto;
import com.example.javaopencommerce.rest.services.OrderDetailsDtoService;
import io.smallrye.mutiny.Uni;
import java.math.BigDecimal;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
    public Uni<ItemEntity> test1() {
        ItemEntity item = ItemEntity.builder()
                .stock(3)
                .valueGross(BigDecimal.ONE)
                .vat(0.23)
                .image(ImageEntity.builder().id(2L).build())
                .build();
        return this.itemRepository.saveItem(item);
    }

    @GET
    @Path("/get_order")
    public Uni<OrderDetailsDto> getOrderDetailsById(@QueryParam("id") Long id) {
        return this.orderDetailsDtoService.getOrderDetailsById(id);
    }

    @POST
    @Path("/save_order")
    public Uni<OrderDetailsDto> testSaveOrder(OrderDetailsDto orderDetails) {
        return this.orderDetailsDtoService.saveOrderDetails(Uni.createFrom().item(orderDetails));
    }
}
