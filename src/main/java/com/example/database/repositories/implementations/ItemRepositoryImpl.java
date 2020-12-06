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
import java.util.*;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@ApplicationScoped
public class ItemRepositoryImpl implements ItemRepository {

    private final PgPool client;
    private static final String SELECT_ITEM_BASE = "SELECT * FROM ITEM i";
    private static final String SELECT_DETAILS_BASE = "SELECT * FROM ITEMDETAILS i";
    private static final String IMAGE_JOIN = "INNER JOIN Image img ON i.image_id = img.id";
    private static final String CATEGORY_JOIN = "INNER JOIN item_category ON i.id = item_category.item_id";


    public ItemRepositoryImpl(PgPool client) {this.client = client;}


    @Override
    public Uni<List<Item>> getAllItems() {
        return this.client.preparedQuery(format("%s %s %s", SELECT_ITEM_BASE, IMAGE_JOIN, CATEGORY_JOIN))
                .execute()
                .map(this::getItems);
    }

    @Override
    public Uni<Item> getItemById(Long id) {
        return this.client.preparedQuery(format("%s %s WHERE i.id = $1", SELECT_ITEM_BASE, IMAGE_JOIN))
        .execute(Tuple.of(id))
                .map(rs -> {
                    if (rs == null || !rs.iterator().hasNext()) {
                        return Item.builder().build();
                    }
                    return rowToItem(rs.iterator().next());
                });
    }

    @Override
    public Uni<List<Item>> getItemsListByIdList(List<Long> ids) {
        return this.client.preparedQuery(format("%s %s %s WHERE i.id = ANY ($1) ORDER BY i.id DESC",
                                            SELECT_ITEM_BASE, IMAGE_JOIN, CATEGORY_JOIN))
        .execute(Tuple.of(ids.toArray(new Long[ids.size()])))
                .map(this::getItems);
    }

    @Override
    public Uni<List<ItemDetails>> getAllItemDetails() {
        return this.client.preparedQuery(SELECT_DETAILS_BASE)
                .execute()
                .map(this::getItemDetails);
    }

    @Override
    public Uni<List<ItemDetails>> getItemDetailsListByItemId(Long id) {
        return this.client.preparedQuery(format("%s WHERE item_id = $1", SELECT_DETAILS_BASE))
                .execute(Tuple.of(id))
                .map(this::getItemDetails);
    }

    @Override
    public Uni<List<ItemDetails>> getItemDetailsListByIdList(List<Long> ids) {
        return this.client.preparedQuery(format("%s WHERE item_id = ANY ($1)", SELECT_DETAILS_BASE))
                .execute(Tuple.of(ids.toArray(new Long[ids.size()])))
                .map(this::getItemDetails);
    }

    @Override
    public Uni<Item> saveItem(Item item) {
        return this.client.preparedQuery("INSERT INTO ITEM (stock, valuegross, vat, image_id, producer_id) " +
                "VALUES($1, $2, $3, $4, $5)")
                .execute(Tuple.of(
                    item.getStock(),
                    item.getValueGross(),
                    item.getVat(),
                    item.getImage().getId(),
                    item.getProducerId())
                ).map(rs -> {
            if (rs == null || !rs.iterator().hasNext()) {
                return Item.builder().build();
            }
            return rowToItem(rs.iterator().next());
        });
    }

    @Override
    public Uni<ItemDetails> saveItemDetails(ItemDetails itemDetails) {
        return this.client.preparedQuery("INSERT INTO ITEMDETAILS (description, lang, name, item_id) " +
                "VALUES($1, $2, $3, $4)")
                .execute(Tuple.of(
                        itemDetails.getDescription(),
                        itemDetails.getLang().toLanguageTag(),
                        itemDetails.getName(),
                        itemDetails.getItemId()
               )).map(rs -> {
            if (rs == null || !rs.iterator().hasNext()) {
                return ItemDetails.builder().build();
            }
            return rowToItemDetails(rs.iterator().next());
        });
    }

    //--Helpers-------------------------------------------------------------------------------------------------------

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

        Long imageId = row.getLong("image_id");

        Image image = ofNullable(imageId)
                .map(id -> Image.builder()
                .id(imageId)
                .alt(row.getString("alt"))
                .url(row.getString("url"))
                .build())
                .orElse(Image.builder().build());

        return Item.builder()
                .stock(row.getInteger("stock"))
                .valueGross(BigDecimal.valueOf(row.getDouble("valuegross")))
                .vat(row.getDouble("vat"))
                .id(row.getLong("id"))
                .producerId(row.getLong("producer_id"))
                .image(image)
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
