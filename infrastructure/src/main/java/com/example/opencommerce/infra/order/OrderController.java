package com.example.opencommerce.infra.order;

import com.example.opencommerce.app.order.CreateOrderScenario;
import com.example.opencommerce.app.order.commands.CreateOrderCommand;
import com.example.opencommerce.app.order.query.OrderDto;
import com.example.opencommerce.app.order.query.OrderQueryRepository;
import com.example.opencommerce.domain.OrderId;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpServerRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import static com.example.opencommerce.infra.order.CardController.COOKIE_NAME;
import static java.util.Objects.isNull;

@Slf4j
@Path("order")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderController {

    private final CreateOrderScenario createOrderScenario;
    private final OrderQueryRepository queryRepository;

    OrderController(CreateOrderScenario createOrderScenario, OrderQueryRepository queryRepository) {
        this.createOrderScenario = createOrderScenario;
        this.queryRepository = queryRepository;
    }

    private static void deleteCookie(HttpServerRequest request) {
        request.cookies()
                .add(Cookie.cookie(COOKIE_NAME, "")
                        .setMaxAge(0)
                        .setHttpOnly(true));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response makeOrder(CreateOrderCommand createOrderCommand, @Context HttpServerRequest request) {
        Cookie cardCookie = request.getCookie(COOKIE_NAME);
        if (isNull(cardCookie)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Card does not exists!")
                    .build();
        }
        OrderId orderId = createOrderScenario.createOrder(createOrderCommand.withCardId(cardCookie.getValue()));
        deleteCookie(request);
        return Response.ok(toJson(orderId))
                .build();
    }

    @GET
    @Path("/{id}")
    public OrderDto getOrderById(@PathParam("id") UUID id) {
        return queryRepository.findOrderById(id);
    }

    private String toJson(OrderId orderId) {
        return "{ \"orderId\":\"" + orderId.asUUID() + "\" }";
    }
}
