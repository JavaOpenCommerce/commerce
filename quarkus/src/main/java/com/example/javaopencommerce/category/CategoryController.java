package com.example.javaopencommerce.category;

import static java.util.Collections.emptyList;

import com.example.javaopencommerce.category.dtos.CategoryDto;
import io.smallrye.mutiny.Uni;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryController {

  private final CategoryQueryRepository queryRepository;

  public CategoryController(
      CategoryQueryRepository queryRepository) {
    this.queryRepository = queryRepository;
  }

  @GET
  @Path("/all")
  public Uni<List<CategoryDto>> getAllCategories() {
    return queryRepository.getAll();
  }

  @GET
  @Path("/sub")
  public Uni<List<CategoryDto>> getAllSubcategoriesOf(@QueryParam("id") Long categoryId) {
    //TODO
    return Uni.createFrom().item(emptyList());
  }
}
