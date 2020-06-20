package com.example.quarkus.app;

import com.example.rest.dtos.PageDto;
import com.example.rest.services.StoreDtoService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TestController {

    private final StoreDtoService service;

    public TestController(StoreDtoService service) {this.service = service;}

    @GET
    @Path("/items/category/{categoryId}")
    public PageDto getAllByCategory(@PathParam("categoryId") Long id,
            @QueryParam("page") int page,
            @QueryParam("size") int size) {
        return service.getItemsPageByCategory(id, page, size);
    }

    @GET
    @Path("/items/producer/{producerId}")
    public PageDto getAllByProducer(@PathParam("producerId") Long id,
            @QueryParam("page") int page,
            @QueryParam("size") int size) {
        return service.getItemsPageByProducer(id, page, size);
    }
}
