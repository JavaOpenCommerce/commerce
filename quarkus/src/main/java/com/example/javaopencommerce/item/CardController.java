package com.example.javaopencommerce.item;

import static com.example.javaopencommerce.statics.MessagesStore.OK;
import static java.util.Optional.ofNullable;

import com.example.javaopencommerce.item.dtos.CardDto;
import com.example.javaopencommerce.item.dtos.ItemDto;
import com.example.javaopencommerce.item.dtos.ProductOrder;
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

  private final CardFacade cardFacade;
  private final ItemQueryRepository itemQueryRepository;
  private static final String COOKIE_NAME = "CardCookie";

  public CardController(CardFacade cardFacade,
      ItemQueryRepository itemQueryRepository) {
    this.cardFacade = cardFacade;
    this.itemQueryRepository = itemQueryRepository;
  }


  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public CardDto getCard(@Context HttpServerRequest request) {
    addCookieIfNotPresent();
    return this.cardFacade.getCard(request.getCookie(COOKIE_NAME).getValue());
  }

  @POST
  @Path("/add")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String addProduct(ProductOrder product) {
    addCookieIfNotPresent();
    String response = this.cardFacade.addProductWithAmount(product,
        this.request.getCookie(COOKIE_NAME).getValue());
    return simpleResponse(response);
  }

  @PUT
  @Path("/increase")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String increaseProductAmount(@QueryParam("id") Long id) {
    String response = this.cardFacade.increaseProductAmount(id,
        this.request.getCookie(COOKIE_NAME).getValue());
    return simpleResponse(response);
  }

  @PUT
  @Path("/decrease")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String decreaseProductAmount(@QueryParam("id") Long id) {
    String response = this.cardFacade.decreaseProductAmount(id,
        this.request.getCookie(COOKIE_NAME).getValue());
    return simpleResponse(response);
  }

  @DELETE
  @Path("/remove")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String removeProduct(@QueryParam("id") Long id) {
    String response = this.cardFacade.removeProduct(id,
        this.request.getCookie(COOKIE_NAME).getValue());
    return simpleResponse(response);
  }

  @DELETE
  @Path("/flush")
  @Produces(MediaType.APPLICATION_JSON)
  public String flushCard() {
    if (!cookieCheck()) {
      this.cardFacade.flushCard(this.request.getCookie(COOKIE_NAME).getValue());
      log.info("Card flushed");
    }
    return simpleResponse(OK);
  }

  @GET
  @Path("/shipping")
  @Produces(MediaType.APPLICATION_JSON)
  public List<ItemDto> getShippingMethods() {
    return itemQueryRepository.getShippingMethods();
  }

  private boolean cookieCheck() {
    return ofNullable(this.request.getCookie(COOKIE_NAME)).map(Cookie::getValue).isEmpty();
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
    return UUID.randomUUID().toString();
  }

  private String simpleResponse(String response) {
    return "{\"response\":\"" + response + "\"}";
  }
}
