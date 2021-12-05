package com.example.javaopencommerce.address;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

import com.example.javaopencommerce.CommonRow;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import java.util.List;

class AddressMapper {

    private static final String ID = "id";
    private static final String CITY = "city";
    private static final String LOCAL = "local";
    private static final String STREET = "street";
    private static final String ZIP = "zip";


    public List<AddressEntity> rowSetToAddressList(RowSet<Row> rs) {
        if (CommonRow.isRowSetEmpty(rs)) {
            return emptyList();
        }

        return stream(rs.spliterator(), false)
                .map(this::rowToAddress)
                .collect(toList());
    }

    public AddressEntity rowToAddress(Row row) {
        if (isNull(row)) {
            return AddressEntity.builder().build();
        }

        return AddressEntity.builder()
                .id(row.getLong(ID))
                .city(row.getString(CITY))
                .local(row.getString(LOCAL))
                .street(row.getString(STREET))
                .zip(row.getString(ZIP))
                .build();
    }
}
