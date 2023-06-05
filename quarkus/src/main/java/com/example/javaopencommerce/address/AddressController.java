package com.example.javaopencommerce.address;

import com.example.javaopencommerce.address.dtos.AddressDto;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("addresses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddressController {

  private final AddressQueryRepository queryRepository;

  public AddressController(
      AddressQueryRepository queryRepository) {
    this.queryRepository = queryRepository;
  }

  @GET
  @Path("/{id}")
  public AddressDto getAddressById(@PathParam("id") Long addressId) {
    return queryRepository.findById(addressId);
  }

}
