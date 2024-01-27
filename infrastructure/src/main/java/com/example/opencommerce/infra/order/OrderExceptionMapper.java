package com.example.opencommerce.infra.order;

import com.example.opencommerce.domain.order.exceptions.ordervalidation.OrderException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class OrderExceptionMapper implements ExceptionMapper<OrderException> {

    @Override
    public Response toResponse(OrderException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .build();
    }
}
