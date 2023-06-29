package com.example.javaopencommerce.order;

import com.example.javaopencommerce.order.exceptions.ordervalidation.OrderException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class OrderExceptionMapper implements ExceptionMapper<OrderException> {

  @Override
  public Response toResponse(OrderException exception) {
    return Response.status(Status.BAD_REQUEST)
        .build();
  }
}
