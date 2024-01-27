package com.example.opencommerce.infra.commonexceptionmappers;


import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;

@Provider
public class CommonJsonProcessingExceptionMapper implements ExceptionMapper<ProcessingException> {

    @Override
    public Response toResponse(ProcessingException exception) {
        return Response.status(BAD_REQUEST)
                .entity(getRootCause(exception).getMessage())
                .build();
    }
}
