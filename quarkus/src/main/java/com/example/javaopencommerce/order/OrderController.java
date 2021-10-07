package com.example.javaopencommerce.order;

import com.example.javaopencommerce.order.dtos.OrderDetailsDto;
import com.example.javaopencommerce.order.exceptions.OrderValidationException;
import io.smallrye.mutiny.Uni;
import java.util.function.Supplier;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("order")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderController {

  private final OrderFacade orderFacade;
  private final OrderQueryRepository queryRepository;

  public OrderController(OrderFacade orderFacade,
      OrderQueryRepository queryRepository) {
    this.orderFacade = orderFacade;
    this.queryRepository = queryRepository;
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<OrderDetailsDto> makeOrder(OrderDetailsDto orderDetailsDto) {
    return orderFacade.makeOrder(orderDetailsDto);
  }

  @GET
  @Path("/{id}")
  public Uni<OrderDetailsDto> getOrderById(@PathParam("id") Long id) {
    return queryRepository.findOrderDetailsById(id);
  }
}
