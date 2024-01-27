package com.example.opencommerce.infra.catalog;

import com.example.opencommerce.app.catalog.query.CatalogQueryRepository;
import com.example.opencommerce.app.catalog.query.CategoryDto;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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
