package com.example.javaopencommerce.order;

import com.example.javaopencommerce.order.dtos.CreateOrderDto;
import com.example.javaopencommerce.order.dtos.OrderDto;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpServerRequest;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

import static com.example.javaopencommerce.order.CardController.COOKIE_NAME;
import static java.util.Objects.isNull;

@Slf4j
@Path("order")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderController {

    private final OrderFacade orderFacade;
    private final OrderQueryRepository queryRepository;

    public OrderController(OrderFacade orderFacade, OrderQueryRepository queryRepository) {
        this.orderFacade = orderFacade;
        this.queryRepository = queryRepository;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public OrderDto makeOrder(CreateOrderDto createOrderDto, @Context HttpServerRequest request) {
        Cookie cardCookie = request.getCookie(COOKIE_NAME);
        if (isNull(cardCookie)) {
            throw new IllegalStateException("Card does not exists!"); // TODO REFACTOR
        }
        return orderFacade.createOrder(createOrderDto, cardCookie.getValue());
    }

    @GET
    @Path("/{id}")
    public OrderDto getOrderById(@PathParam("id") UUID id) {
        return queryRepository.findOrderById(id);
    }
}
