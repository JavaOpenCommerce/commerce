package com.example.opencommerce.infra.order;

import com.example.opencommerce.app.catalog.query.ItemDto;
import com.example.opencommerce.app.catalog.query.ItemQueryRepository;
import com.example.opencommerce.app.order.CardOperations;
import com.example.opencommerce.app.order.query.CardDto;
import com.example.opencommerce.app.order.query.ProductOrder;
import com.example.opencommerce.domain.Amount;
import com.example.opencommerce.domain.ItemId;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpServerRequest;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

import static com.example.opencommerce.statics.MessagesStore.OK;
import static java.util.Optional.ofNullable;

@Slf4j
@Path("card")
public class CardController {

    static final String COOKIE_NAME = "CardCookie";
    private final CardOperations cardOperations;
    private final ItemQueryRepository productQueryRepository;
    @Context
    private HttpServerRequest request;

    public CardController(CardOperations cardOperations, ItemQueryRepository productQueryRepository) {
        this.cardOperations = cardOperations;
        this.productQueryRepository = productQueryRepository;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public CardDto getCard(@Context HttpServerRequest request) {
        addCookieIfNotPresent();
        return this.cardOperations.getCard(request.getCookie(COOKIE_NAME)
                .getValue());
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CardDto addItem(ProductOrder product) {
        addCookieIfNotPresent();
        ItemId itemId = ItemId.of(product.getItemId());
        Amount amount = Amount.of(product.getAmount());
        return this.cardOperations.addItemWithAmount(itemId, amount, this.request.getCookie(COOKIE_NAME)
                .getValue());
    }

    @PUT
    @Path("/increase")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CardDto increaseItemAmount(@QueryParam("id") Long id) {
        ItemId itemId = ItemId.of(id);
        return this.cardOperations.addItemWithAmount(itemId, Amount.of(1), this.request.getCookie(COOKIE_NAME)
                .getValue());
    }

    @PUT
    @Path("/change-amount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CardDto changeItemAmount(ProductOrder product) {
        ItemId itemId = ItemId.of(product.getItemId());
        Amount amount = Amount.of(product.getAmount());
        return this.cardOperations.changeItemAmount(itemId, amount, this.request.getCookie(COOKIE_NAME)
                .getValue());
    }

    @DELETE
    @Path("/remove")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CardDto removeProduct(@QueryParam("id") Long id) {
        ItemId itemId = ItemId.of(id);
        return this.cardOperations.removeProduct(itemId, this.request.getCookie(COOKIE_NAME)
                .getValue());
    }

    @DELETE
    @Path("/flush")
    @Produces(MediaType.APPLICATION_JSON)
    public String flushCard() {
        if (!cookieCheck()) {
            this.cardOperations.flushCard(this.request.getCookie(COOKIE_NAME)
                    .getValue());
            log.info("Card flushed");
        }
        return simpleResponse(OK);
    }

    @GET
    @Path("/shipping")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ItemDto> getShippingMethods() {
        return productQueryRepository.getShippingMethods();
    }

    private boolean cookieCheck() {
        return ofNullable(this.request.getCookie(COOKIE_NAME)).map(Cookie::getValue)
                .isEmpty();
    }

    private void addCookieIfNotPresent() {
        if (cookieCheck()) {
            log.info("No cookie named '" + COOKIE_NAME + "' found, generating new.");
            String newKey = generateValue();
            this.request.cookies()
                    .add(Cookie.cookie(COOKIE_NAME, newKey)
                            .setMaxAge(60L * 60L * 1000L)
                            .setHttpOnly(true));
            log.info("New Cookie generated: " + newKey);
        }
    }

    private String generateValue() {
        return UUID.randomUUID()
                .toString();
    }

    private String simpleResponse(String response) {
        return "{\"response\":\"" + response + "\"}";
    }
}
