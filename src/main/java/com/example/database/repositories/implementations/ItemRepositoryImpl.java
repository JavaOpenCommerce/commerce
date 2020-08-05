package com.example.database.repositories.implementations;

import com.example.database.entity.Image;
import com.example.database.entity.Item;
import com.example.database.entity.ItemDetails;
import com.example.database.repositories.interfaces.ItemRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@ApplicationScoped
public class ItemRepositoryImpl implements ItemRepository {

    private final PgPool client;

    public ItemRepositoryImpl(PgPool client) {this.client = client;}


    @Override
    public Uni<List<Item>> getAllItems() {
        return client.preparedQuery("SELECT * FROM Item i " +
                                        "INNER JOIN Image img ON i.image_id = img.id " +
                                        "INNER JOIN item_category ON item_category.item_id = i.id ")
                .onItem().apply(rs -> getItems(rs));
    }

    @Override
    public Uni<Item> getItemById(Long id) {
        return client.preparedQuery("SELECT * FROM Item i " +
                                        "INNER JOIN Image img ON i.image_id = img.id " +
                                        "WHERE i.id = $1", Tuple.of(id))
                .onItem().apply(rs -> {
                    if (rs == null || !rs.iterator().hasNext()) {
                        return Item.builder().build();
                    }
                    return rowToItem(rs.iterator().next());
                });
    }

    @Override
    public Uni<List<Item>> getItemsListByIdList(List<Long> ids) {
        return client.preparedQuery("SELECT * FROM Item i " +
                                        "INNER JOIN Image img ON i.image_id = img.id " +
                                        "INNER JOIN item_category ON item_category.item_id = i.id " +
                                        "WHERE i.id = ANY ($1) ORDER BY i.id DESC", Tuple.of(ids.toArray(new Long[ids.size()])))
                .onItem().apply(rs -> getItems(rs));
    }

    @Override
    public Uni<List<ItemDetails>> getAllItemDetails() {
        return client.preparedQuery("SELECT * FROM ITEMDETAILS")
                .onItem().apply(rs -> getItemDetails(rs));
    }

    @Override
    public Uni<List<ItemDetails>> getItemDetailsListByItemId(Long id) {
        return client.preparedQuery("SELECT * FROM ITEMDETAILS WHERE item_id = $1", Tuple.of(id))
                .onItem().apply(rs -> getItemDetails(rs));
    }

    @Override
    public Uni<List<ItemDetails>> getItemDetailsListByIdList(List<Long> ids) {
        return client.preparedQuery("SELECT * FROM ITEMDETAILS WHERE item_id = ANY ($1)",
                                        Tuple.of(ids.toArray(new Long[ids.size()])))
                .onItem().apply(rs -> getItemDetails(rs));
    }

    private List<Item> getItems(RowSet<Row> rs) {
        if (rs == null) {
            return emptyList();
        }

        Map<Long, Item> items = new HashMap<>();
        for (Row row : rs) {
            Item item = rowToItem(row);
            ofNullable(items.putIfAbsent(item.getId(), item))
                    .ifPresentOrElse(
                            it -> it
                                    .getCategoryIds()
                                    .add(row.getLong("category_id")),
                            () -> items
                                    .get(item.getId())
                                    .getCategoryIds()
                                    .add(row.getLong("category_id")));
        }
        return new ArrayList<>(items.values());
    }

    private Item rowToItem(Row row) {
        if (row == null) {
            return Item.builder().build();
        }

        return Item.builder()
                .stock(row.getInteger("stock"))
                .valueGross(BigDecimal.valueOf(row.getDouble("valuegross")))
                .vat(row.getDouble("vat"))
                .id(row.getLong("id"))
                .producerId(row.getLong("producer_id"))
                .image(Image.builder()
                        .id(row.getLong("image_id"))
                        .alt(row.getString("alt"))
                        .url(row.getString("url"))
                        .build())
                .build();
    }

    private List<ItemDetails> getItemDetails(RowSet<Row> rs) {
        if (rs == null) {
            return emptyList();
        }

        return stream(rs.spliterator(), false)
                .map(this::rowToItemDetails)
                .collect(toList());
    }

    private ItemDetails rowToItemDetails(Row row) {
        if (row == null) {
            return ItemDetails.builder().build();
        }

        return ItemDetails.builder()
                .id(row.getLong("id"))
                .description(row.getString("description"))
                .lang(Locale.forLanguageTag(row.getString("lang")))
                .name(row.getString("name"))
                .itemId(row.getLong("item_id"))
                .build();
    }
}
