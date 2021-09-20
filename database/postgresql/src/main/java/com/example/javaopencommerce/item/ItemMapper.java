package com.example.javaopencommerce.item;

import com.example.javaopencommerce.image.ImageEntity;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;

import java.math.BigDecimal;
import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

class ItemMapper {

    private static final String ID = "id";
    private static final String ITEM_ID = "item_id";
    private static final String IMAGE_ID = "image_id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String LANG = "lang";
    private static final String ALT = "alt";
    private static final String URL = "url";
    private static final String STOCK = "stock";
    private static final String VAT = "vat";
    private static final String GROSS = "value_gross";


    public List<ItemEntity> getItems(RowSet<Row> rs) {
        if (rs == null) {
            return emptyList();
        }

        Map<Long, ItemEntity> items = new HashMap<>();
        for (Row row : rs) {
            ItemEntity item = rowToItem(row);
            items.put(item.getId(), item);
        }
        return new ArrayList<>(items.values());
    }

    public ItemEntity rowToItem(Row row) {
        if (row == null) {
            return ItemEntity.builder().build();
        }

        Long imageId = row.getLong(IMAGE_ID);

        ImageEntity image = ofNullable(imageId)
                .map(id -> ImageEntity.builder()
                        .id(imageId)
                        .alt(row.getString(ALT))
                        .url(row.getString(URL))
                        .build())
                .orElse(ImageEntity.builder().build());

        return ItemEntity.builder()
                .stock(row.getInteger(STOCK))
                .valueGross(BigDecimal.valueOf(row.getDouble(GROSS)))
                .vat(row.getDouble(VAT))
                .id(row.getLong(ID))
                .image(image)
                .build();
    }

    public List<ItemDetailsEntity> getItemDetails(RowSet<Row> rs) {
        if (rs == null) {
            return emptyList();
        }

        return stream(rs.spliterator(), false)
                .map(this::rowToItemDetails)
                .collect(toList());
    }

    public ItemDetailsEntity rowToItemDetails(Row row) {
        if (row == null) {
            return ItemDetailsEntity.builder().build();
        }

        return ItemDetailsEntity.builder()
                .id(row.getLong(ID))
                .description(row.getString(DESCRIPTION))
                .lang(Locale.forLanguageTag(row.getString(LANG)))
                .name(row.getString(NAME))
                .itemId(row.getLong(ITEM_ID))
                .build();
    }
}
