package com.example.database.repositories.impl.mappers;

import com.example.database.entity.Address;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

import static com.example.utils.CommonRow.isRowSetEmpty;
import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@ApplicationScoped
public class AddressMapper {

    private static final String ID = "id";
    private static final String CITY = "city";
    private static final String LOCAL = "local";
    private static final String STREET = "street";
    private static final String ZIP = "zip";
    private static final String USER_ID = "user_id";


    public List<Address> rowSetToAddressList(RowSet<Row> rs) {
        if (isRowSetEmpty(rs)) {
            return emptyList();
        }

        return stream(rs.spliterator(), false)
                .map(this::rowToAddress)
                .collect(toList());
    }

    public Address rowToAddress(Row row) {
        if (isNull(row)) {
            return Address.builder().build();
        }

        return Address.builder()
                .id(row.getLong(ID))
                .city(row.getString(CITY))
                .local(row.getString(LOCAL))
                .street(row.getString(STREET))
                .zip(row.getString(ZIP))
                .userId(row.getLong(USER_ID))
                .build();
    }
}
