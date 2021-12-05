package com.example.javaopencommerce.address;

import static com.example.javaopencommerce.CommonRow.isRowSetEmpty;

import com.example.javaopencommerce.exception.EntityNotFoundException;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;
import java.util.List;

class PsqlAddressRepositoryImpl implements PsqlAddressRepository {

  private final PgPool client;
  private final AddressMapper addressMapper;

  PsqlAddressRepositoryImpl(PgPool client,
      AddressMapper addressMapper) {
    this.client = client;
    this.addressMapper = addressMapper;
  }

  @Override
  public Uni<List<AddressEntity>> findByZip(String zip) {
    return this.client.preparedQuery("SELECT * FROM ADDRESS WHERE zip = $1")
        .execute(Tuple.of(zip))
        .map(this.addressMapper::rowSetToAddressList);
  }

  @Override
  public Uni<AddressEntity> findById(Long id) {
    return this.client.preparedQuery("SELECT * FROM ADDRESS WHERE id = $1")
        .execute(Tuple.of(id))
        .map(rs -> {
          if (isRowSetEmpty(rs)) {
            throw new EntityNotFoundException("Address", id);
          }
          return this.addressMapper.rowToAddress(rs.iterator().next());
        });
  }

  @Override
  public Uni<List<AddressEntity>> getUserAddressListById(Long id) {
    throw new UnsupportedOperationException();
  }
}
