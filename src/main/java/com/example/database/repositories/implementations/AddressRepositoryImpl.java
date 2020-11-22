package com.example.database.repositories.implementations;

import com.example.database.entity.Address;
import com.example.database.repositories.interfaces.AddressRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@ApplicationScoped
public class AddressRepositoryImpl implements AddressRepository {

    private final PgPool client;

    public AddressRepositoryImpl(PgPool client) {
        this.client = client;
    }

    @Override
    public Uni<List<Address>> findByZip(String zip) {
        return this.client.preparedQuery("SELECT * FROM ADDRESS WHERE zip = $1")
                .execute(Tuple.of(zip))
                .onItem().apply(this::rowSetToAddressList);
    }

    @Override
    public Uni<Address> findById(Long id) {
        return this.client.preparedQuery("SELECT * FROM ADDRESS WHERE id = $1")
                .execute(Tuple.of(id))
                .onItem().apply(rs -> {
                    if (isRowSetEmpty(rs)) {
                        return Address.builder().build();
                    }
                    return rowToAddress(rs.iterator().next());
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


    //--Helpers-----------------------------------------------------------------------------------------------------

    private List<Address> rowSetToAddressList(RowSet<Row> rs) {
        if (isRowSetEmpty(rs)) {
            return emptyList();
        }

        return stream(rs.spliterator(), false)
                .map(this::rowToAddress)
                .collect(toList());
    }

    private Address rowToAddress(Row row) {
        if (isNull(row)) {
            return Address.builder().build();
        }

        return Address.builder()
                .id(row.getLong("id"))
                .city(row.getString("city"))
                .local(row.getString("local"))
                .street(row.getString("street"))
                .zip(row.getString("zip"))
                .userId(row.getLong("user_id"))
                .build();
    }

    private boolean isRowSetEmpty(RowSet<Row> rs) {
        return isNull(rs) || !rs.iterator().hasNext();
    }
}
