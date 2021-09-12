package com.example.database.repositories.impl;


import static com.example.utils.CommonRow.isRowSetEmpty;

import com.example.database.repositories.impl.mappers.AddressMapper;
import com.example.javaopencommerce.address.AddressEntity;
import com.example.javaopencommerce.address.AddressRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AddressRepositoryImpl implements AddressRepository {

    private final PgPool client;
    private final AddressMapper addressMapper;

    public AddressRepositoryImpl(PgPool client, AddressMapper addressMapper) {
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
                        return AddressEntity.builder().build();
                    }
                    return this.addressMapper.rowToAddress(rs.iterator().next());
                });
    }

    @Override
    public Uni<List<AddressEntity>> getAddressByCity(String city) {
        return null;
    }

    @Override
    public Uni<List<AddressEntity>> getUserAddressListById(Long id) {
        return null;
    }

}
