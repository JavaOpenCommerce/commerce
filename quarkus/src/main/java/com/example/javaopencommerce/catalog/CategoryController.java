package com.example.javaopencommerce.catalog;

import com.example.javaopencommerce.catalog.query.CatalogQueryRepository;
import com.example.javaopencommerce.catalog.query.CategoryDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("catalog")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryController {

    private final CatalogQueryRepository queryRepository;

    public CategoryController(CatalogQueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    @GET
    @Path("/")
    public CategoryDto getCatalog() {
        return queryRepository.getCatalog();
    }
}
