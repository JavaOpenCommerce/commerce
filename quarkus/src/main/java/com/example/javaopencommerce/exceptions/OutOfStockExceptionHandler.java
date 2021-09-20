package com.example.javaopencommerce.exceptions;


import com.example.javaopencommerce.item.exceptions.OutOfStockException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
class OutOfStockExceptionHandler implements ExceptionMapper<OutOfStockException> {

    @Override
    public Response toResponse(OutOfStockException exception) {
        return Response
                .status(Response.Status.NOT_ACCEPTABLE)
                .entity(exception.getMessage())
                .build();
    }
}
