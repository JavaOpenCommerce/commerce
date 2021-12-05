package com.example.javaopencommerce.image;

import com.example.javaopencommerce.image.dtos.ImageDto;
import io.smallrye.mutiny.Uni;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("images")
public class ImageController {

  private final ImageQueryRepository queryRepository;

  public ImageController(ImageQueryRepository queryRepository) {
    this.queryRepository = queryRepository;
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<ImageDto> getImageById(@PathParam("id") Long id) {
    return this.queryRepository.getImageById(id);
  }

}
