package com.example.javaopencommerce.order;

import static com.example.javaopencommerce.statics.MessagesStore.OK;
import static java.util.Optional.ofNullable;

import com.example.javaopencommerce.catalog.ItemQueryRepository;
import com.example.javaopencommerce.catalog.dtos.ItemDto;
import com.example.javaopencommerce.order.dtos.CardDto;
import com.example.javaopencommerce.order.dtos.ProductOrder;
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

  static final String COOKIE_NAME = "CardCookie";
  private final CardFacade cardFacade;
  private final ItemQueryRepository productQueryRepository;
  @Context
  private HttpServerRequest request;

  public CardController(CardFacade cardFacade, ItemQueryRepository productQueryRepository) {
    this.cardFacade = cardFacade;
    this.productQueryRepository = productQueryRepository;
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
  public CardDto addItem(ProductOrder product) {
    addCookieIfNotPresent();
    return this.cardFacade.addItemWithAmount(product.getItemId(), product.getAmount(),
        this.request.getCookie(COOKIE_NAME).getValue());
  }

  @PUT
  @Path("/increase")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public CardDto increaseItemAmount(@QueryParam("id") Long id) {
    return this.cardFacade.addItemWithAmount(id, 1, this.request.getCookie(COOKIE_NAME).getValue());
  }

  @PUT
  @Path("/change-amount")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public CardDto changeItemAmount(ProductOrder product) {
    return this.cardFacade.changeItemAmount(product.getItemId(), product.getAmount(),
        this.request.getCookie(COOKIE_NAME).getValue());
  }

  @DELETE
  @Path("/remove")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public CardDto removeProduct(@QueryParam("id") Long id) {
    return this.cardFacade.removeProduct(id, this.request.getCookie(COOKIE_NAME).getValue());
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
    return productQueryRepository.getShippingMethods();
  }

  private boolean cookieCheck() {
    return ofNullable(this.request.getCookie(COOKIE_NAME)).map(Cookie::getValue).isEmpty();
  }

  private void addCookieIfNotPresent() {
    if (cookieCheck()) {
      log.info("No cookie named '" + COOKIE_NAME + "' found, generating new.");
      String newKey = generateValue();
      this.request.cookies()
          .add(Cookie.cookie(COOKIE_NAME, newKey).setMaxAge(60L * 60L * 1000L).setHttpOnly(true));
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
