package com.example.javaopencommerce.image;

import static java.util.stream.StreamSupport.stream;

import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import java.util.List;
import java.util.stream.Collectors;

class ImageMapper {

  private static final String ID = "id";
  private static final String ALT = "alt";
  private static final String URL = "url";

  public ImageEntity rowToImage(Row row) {
    if (row == null) {
      return ImageEntity.builder().build();
    }

    return ImageEntity.builder()
        .id(row.getLong(ID))
        .alt(row.getString(ALT))
        .url(row.getString(URL))
        .build();
  }

  public List<ImageEntity> getImages(RowSet<Row> rs) {
    return stream(rs.spliterator(), false)
        .map(this::rowToImage)
        .collect(Collectors.toList());
  }
}
