package com.example.javaopencommerce.quarkus.app;

import static com.example.javaopencommerce.statics.MessagesStore.OK;
import static java.util.Optional.ofNullable;

import com.example.javaopencommerce.item.ItemDto;
import com.example.javaopencommerce.order.CardDto;
import com.example.javaopencommerce.order.CardProductEntity;
import com.example.javaopencommerce.rest.services.CardDtoService;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpServerRequest;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("card")
public class CardController {

    @Context
    private HttpServerRequest request;

    private final CardDtoService cardDtoService;
    private static final String COOKIE_NAME = "CardCookie";

    public CardController(CardDtoService cardDtoService) {
        this.cardDtoService = cardDtoService;
    }


    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<CardDto> getCard(@Context HttpServerRequest request) {
        addCookieIfNotPresent();
        return this.cardDtoService.getCard(request.getCookie(COOKIE_NAME).getValue());
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<String> addProduct(CardProductEntity product) {
        addCookieIfNotPresent();
        return this.cardDtoService.addProductWithAmount(product, this.request.getCookie(COOKIE_NAME).getValue())
                .map(this::simpleResponse);
    }

    @PUT
    @Path("/increase")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<String> increaseProductAmount(@QueryParam("id") Long id) {
        return this.cardDtoService.increaseProductAmount(id, this.request.getCookie(COOKIE_NAME).getValue())
                .map(this::simpleResponse);
    }

    @PUT
    @Path("/decrease")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<String> decreaseProductAmount(@QueryParam("id") Long id) {
        return this.cardDtoService.decreaseProductAmount(id, this.request.getCookie(COOKIE_NAME).getValue())
                .map(this::simpleResponse);
    }

    @DELETE
    @Path("/remove")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<String> removeProduct(@QueryParam("id") Long id) {
        return this.cardDtoService.removeProduct(id, this.request.getCookie(COOKIE_NAME).getValue())
                .map(this::simpleResponse);
    }

    @DELETE
    @Path("/flush")
    @Produces(MediaType.APPLICATION_JSON)
    public String flushCard() {
        if (!cookieCheck()) {
            this.cardDtoService.flushCard(this.request.getCookie(COOKIE_NAME).getValue());
            log.info("Card flushed");
        }
        return simpleResponse(OK);
    }

    @GET
    @Path("/shipping")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<ItemDto>> getShippingMethods() {
        return this.cardDtoService.getShippingMethods();
    }

    private boolean cookieCheck() {
        return ofNullable(this.request.getCookie(COOKIE_NAME)).map(Cookie::getValue).isEmpty();
    }

    private void addCookieIfNotPresent() {
        if (cookieCheck()) {
            log.info("No cookie named '" + COOKIE_NAME + "' found, generating new.");
            String newKey = generateValue();
            this.request.cookieMap()
                    .put(COOKIE_NAME, Cookie.cookie(COOKIE_NAME, newKey)
                            .setMaxAge(60L * 60L * 1000L)
                            .setHttpOnly(true));
            log.info("New Cookie generated: " + newKey);
        }
    }

    private String generateValue() {
        return UUID.randomUUID().toString();
    }

    private String simpleResponse(String response) {
        return "{\"response\":\"" + response + "\"}";
    }
}
