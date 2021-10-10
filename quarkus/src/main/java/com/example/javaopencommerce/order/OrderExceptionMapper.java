package com.example.javaopencommerce.order;

import com.example.javaopencommerce.order.exceptions.OrderException;
import com.example.javaopencommerce.order.exceptions.OrderPersistenceException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class OrderExceptionMapper implements ExceptionMapper<OrderException> {

  @Override
  public Response toResponse(OrderException exception) {
    if (exception instanceof OrderPersistenceException) {
      return Response.status(Status.PRECONDITION_FAILED)
          .entity(OrderExceptionDto.builder()
              .type(exception.getClass().getSimpleName())
              .message(exception.getMessage())
              .build())
          .build();
    }
    return Response.status(Status.BAD_REQUEST)
        .build();
  }
}
