package com.example.utils;

import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;

import static java.util.Objects.isNull;

public interface CommonRow {

    static boolean isRowSetEmpty(RowSet<Row> rs) {
        return isNull(rs) || !rs.iterator().hasNext();
    }
}
