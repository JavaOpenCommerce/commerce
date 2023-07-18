package com.example.javaopencommerce.order;

import com.example.javaopencommerce.OrderId;
import com.example.javaopencommerce.order.dtos.CreateOrderDto;
import com.example.javaopencommerce.order.dtos.OrderDto;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpServerRequest;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

    OrderController(OrderFacade orderFacade, OrderQueryRepository queryRepository) {
        this.orderFacade = orderFacade;
        this.queryRepository = queryRepository;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response makeOrder(CreateOrderDto createOrderDto, @Context HttpServerRequest request) {
        Cookie cardCookie = request.getCookie(COOKIE_NAME);
        if (isNull(cardCookie)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Card does not exists!")
                    .build();
        }
        OrderId orderId = orderFacade.createOrder(createOrderDto, cardCookie.getValue());
        deleteCookie(request);
        return Response.ok(toJson(orderId))
                .build();
    }

    @GET
    @Path("/{id}")
    public OrderDto getOrderById(@PathParam("id") UUID id) {
        return queryRepository.findOrderById(id);
    }

    private static void deleteCookie(HttpServerRequest request) {
        request.cookies()
                .add(Cookie.cookie(COOKIE_NAME, "")
                        .setMaxAge(0)
                        .setHttpOnly(true));
    }

    private String toJson(OrderId orderId) {
        return "{ \"orderId\":\"" + orderId.asUUID() + "\" }";
    }
}
