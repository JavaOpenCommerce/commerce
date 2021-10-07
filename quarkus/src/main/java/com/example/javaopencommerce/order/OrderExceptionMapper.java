package com.example.javaopencommerce.order;

import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.order.exceptions.ExceptionWithPayload;
import com.example.javaopencommerce.order.exceptions.OrderValidationException;
import com.example.javaopencommerce.statics.JsonConverter;
import java.util.List;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class OrderExceptionMapper implements ExceptionMapper<OrderValidationException> {

  @Override
  public Response toResponse(OrderValidationException exception) {
    return Response.status(Status.BAD_REQUEST)
        .entity(prepareExceptionResponseBody(exception.getDerivativeExceptions()))
        .build();
  }

  private Object prepareExceptionResponseBody(List<OrderValidationException> orderInaccuracies) {
    return JsonConverter.convertToJson(
        orderInaccuracies.stream()
            .map(this::getOrderExceptionDto)
            .collect(toList()));
  }

  private OrderExceptionDto getOrderExceptionDto(OrderValidationException e) {
    OrderExceptionDto dto = OrderExceptionDto.builder()
        .message(e.getMessage())
        .type(e.getClass().getSimpleName())
        .build();
    if (e instanceof ExceptionWithPayload) {
      dto.setAdditionalData(((ExceptionWithPayload) e).getPayload());
    }
    return dto;
  }
}
