package com.example.opencommerce.infra.commonexceptionmappers;

import com.example.opencommerce.adapters.database.EntityNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;

//TODO add parent DatabaseException and children for not found, persistence and other types of exceptions.

@Provider
public class CommonDatabaseExceptionHandling implements ExceptionMapper<EntityNotFoundException> {

    @Override
    public Response toResponse(EntityNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(getRootCause(exception).getMessage())
                .build();
    }
}
