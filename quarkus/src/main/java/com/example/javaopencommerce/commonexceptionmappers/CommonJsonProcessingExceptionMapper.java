package com.example.javaopencommerce.commonexceptionmappers;

import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CommonJsonProcessingExceptionMapper implements ExceptionMapper<ProcessingException> {

  @Override
  public Response toResponse(ProcessingException exception) {
    return Response.status(Status.BAD_REQUEST)
        .entity(getRootCause(exception).getMessage())
        .build();
  }
}
