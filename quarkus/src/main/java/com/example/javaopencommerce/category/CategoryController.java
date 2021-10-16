package com.example.javaopencommerce.category;

import static java.util.Collections.emptyList;

import com.example.javaopencommerce.category.dtos.CategoryDto;
import io.smallrye.mutiny.Uni;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
  public Uni<List<CategoryDto>> getAllCategoriesThatMatchItemsWithIds(@QueryParam("itemIds") String itemIds) {
    if (itemIds == null || itemIds.isEmpty()) {
      return Uni.createFrom().item(emptyList());
    }
    try {
      List<Long> itemIdsList = Arrays.stream(itemIds.split(","))
          .map(Long::parseLong)
          .collect(Collectors.toList());
      return queryRepository.getCategoriesListByIdList(itemIdsList);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Illegal/corrupted itemsIds query param!");
    }
  }
}
