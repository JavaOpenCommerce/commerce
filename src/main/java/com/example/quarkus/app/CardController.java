package com.example.quarkus.app;

import com.example.database.entity.CardProduct;
import com.example.rest.dtos.CardDto;
import com.example.rest.dtos.ItemDto;
import com.example.rest.services.CardDtoService;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpServerRequest;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

import static com.example.statics.MessagesStore.OK;
import static java.util.Optional.ofNullable;

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
    public Uni<CardDto> getCard() {
        addCookieIfNotPresent();
        return cardDtoService.getCard(request.getCookie(COOKIE_NAME).getValue());
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<String> addProduct(CardProduct product) {
        addCookieIfNotPresent();
        return cardDtoService.addProductWithAmount(product, request.getCookie(COOKIE_NAME).getValue())
                .map(this::simpleResponse);
    }

    @PUT
    @Path("/increase")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<String> increaseProductAmount(@QueryParam("id") Long id) {
        return cardDtoService.increaseProductAmount(id, request.getCookie(COOKIE_NAME).getValue())
                .map(this::simpleResponse);
    }

    @PUT
    @Path("/decrease")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<String> decreaseProductAmount(@QueryParam("id") Long id) {
        return cardDtoService.decreaseProductAmount(id, request.getCookie(COOKIE_NAME).getValue())
                .map(this::simpleResponse);
    }

    @DELETE
    @Path("/remove")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<String> removeProduct(@QueryParam("id") Long id) {
        return cardDtoService.removeProduct(id, request.getCookie(COOKIE_NAME).getValue())
                .map(this::simpleResponse);
    }

    @DELETE
    @Path("/flush")
    @Produces(MediaType.APPLICATION_JSON)
    public String flushCard() {
        if (!cookieCheck()) {
            cardDtoService.flushCard(request.getCookie(COOKIE_NAME).getValue());
            log.info("Card flushed");
        }
        return simpleResponse(OK);
    }

    @GET
    @Path("/shipping")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<ItemDto>> getShippingMethods() {
        return cardDtoService.getShippingMethods();
    }

    private boolean cookieCheck() {
        return ofNullable(request.getCookie(COOKIE_NAME)).map(Cookie::getValue).isEmpty();
    }

    private void addCookieIfNotPresent() {
        if (cookieCheck()) {
            log.info("No cookie named '" + COOKIE_NAME + "' found, generating new.");
            String newKey = generateValue();
            request.cookieMap()
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
