package com.example.javaopencommerce;

import static java.util.Objects.isNull;

import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;

public interface CommonRow {

  static boolean isRowSetEmpty(RowSet<Row> rs) {
    return isNull(rs) || !rs.iterator().hasNext();
  }
}
