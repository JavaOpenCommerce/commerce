package com.example.opencommerce.infra.order;

import com.example.opencommerce.domain.order.exceptions.ExceptionWithPayload;
import com.example.opencommerce.domain.order.exceptions.ordervalidation.OrderValidationException;
import com.example.opencommerce.infra.commonexceptionmappers.BaseExceptionDto;
import com.example.opencommerce.statics.JsonConverter;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Provider
public class OrderValidationExceptionMapper implements ExceptionMapper<OrderValidationException> {

    @Override
    public Response toResponse(OrderValidationException exception) {
        if (exception.getDerivativeExceptions()
                .isEmpty()) {
            return Response.status(Status.PRECONDITION_FAILED)
                    .entity(getBaseExceptionDto(exception))
                    .build();
        }

        return Response.status(Status.PRECONDITION_FAILED)
                .entity(prepareExceptionResponseBody(exception.getDerivativeExceptions()))
                .build();
    }

    private Object prepareExceptionResponseBody(List<OrderValidationException> orderInaccuracies) {
        return JsonConverter.convertToJson(
                orderInaccuracies.stream()
                        .map(this::getBaseExceptionDto)
                        .collect(toList()));
    }

    private BaseExceptionDto getBaseExceptionDto(OrderValidationException e) {
        BaseExceptionDto dto = BaseExceptionDto.builder()
                .message(e.getMessage())
                .type(e.getClass()
                        .getSimpleName())
                .build();
        if (e instanceof ExceptionWithPayload) {
            dto.setAdditionalData(((ExceptionWithPayload) e).getPayload());
        }
        return dto;
    }
}
