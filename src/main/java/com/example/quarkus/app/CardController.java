package com.example.quarkus.app;

import com.example.database.entity.Product;
import com.example.rest.dtos.CardDto;
import com.example.rest.services.CardDtoService;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpServerRequest;
import lombok.extern.jbosslog.JBossLog;
import org.jboss.resteasy.spi.HttpResponseCodes;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@JBossLog
@Path("card")
public class CardController {

    @Context
    private HttpServerRequest request;

    private final CardDtoService cardDtoService;
    private final String COOKIE_NAME = "CardCookie";

    public CardController(CardDtoService cardDtoService) {
        this.cardDtoService = cardDtoService;
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<CardDto> persistCard(Product product) {
        addCookieIfNotPresent();
        return cardDtoService.addProductToCard(product, request.getCookie(COOKIE_NAME).getValue());
    }

    @DELETE
    @Path("/flush")
    @Produces(MediaType.APPLICATION_JSON)
    public String flushCard() {
        if (!cookieCheck()) {
            cardDtoService.flushCard(request.getCookie(COOKIE_NAME).getValue());
            log.info("Card flushed");
        }
        return "{\"response\": " + HttpResponseCodes.SC_OK + "}";
    }

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<CardDto> getCard() {
        addCookieIfNotPresent();
        return cardDtoService.getCard(request.getCookie(COOKIE_NAME).getValue());
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
}
