package com.example.database.repositories.implementations;

import com.example.database.entity.Address;
import com.example.database.repositories.implementations.mappers.AddressMapper;
import com.example.database.repositories.interfaces.AddressRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

import static com.example.utils.CommonRow.isRowSetEmpty;

@ApplicationScoped
public class AddressRepositoryImpl implements AddressRepository {

    private final PgPool client;
    private final AddressMapper addressMapper;

    public AddressRepositoryImpl(PgPool client, AddressMapper addressMapper) {
        this.client = client;
        this.addressMapper = addressMapper;
    }

    @Override
    public Uni<List<Address>> findByZip(String zip) {
        return this.client.preparedQuery("SELECT * FROM ADDRESS WHERE zip = $1")
                .execute(Tuple.of(zip))
                .map(addressMapper::rowSetToAddressList);
    }

    @Override
    public Uni<Address> findById(Long id) {
        return this.client.preparedQuery("SELECT * FROM ADDRESS WHERE id = $1")
                .execute(Tuple.of(id))
                .map(rs -> {
                    if (isRowSetEmpty(rs)) {
                        return Address.builder().build();
                    }
                    return addressMapper.rowToAddress(rs.iterator().next());
                });
    }

    @Override
    public Uni<List<Address>> getAddressByCity(String city) {
        return null;
    }

    @Override
    public Uni<List<Address>> getUserAddressListById(Long id) {
        return null;
    }

}
