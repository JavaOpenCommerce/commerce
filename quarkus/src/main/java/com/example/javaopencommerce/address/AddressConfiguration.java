package com.example.javaopencommerce.address;

import io.vertx.mutiny.pgclient.PgPool;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Singleton
class AddressConfiguration {

  @Produces
  @ApplicationScoped
  PsqlAddressRepository psqlAddressRepository(PgPool sqlClient) {
    return new PsqlAddressRepositoryImpl(sqlClient, new AddressMapper());
  }

  @Produces
  @ApplicationScoped
  AddressRepository addressRepository(PsqlAddressRepository repository) {
    return new AddressRepositoryImpl(repository);
  }

  @Produces
  @ApplicationScoped
  AddressQueryRepository addressQueryRepository(PsqlAddressRepository repository) {
    return new AddressQueryRepositoryImpl(repository);
  }

  @Produces
  @ApplicationScoped
  AddressController addressController(AddressQueryRepository repository) {
    return new AddressController(repository);
  }

}
