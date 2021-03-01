package com.example.database.repositories.implementations;

import com.example.database.entity.Item;
import com.example.database.entity.ItemDetails;
import com.example.database.repositories.implementations.mappers.ItemMapper;
import com.example.database.repositories.interfaces.ItemRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

import static com.example.utils.CommonRow.isRowSetEmpty;
import static java.lang.String.format;

@ApplicationScoped
public class ItemRepositoryImpl implements ItemRepository {

    private final PgPool client;
    private final ItemMapper itemMapper;

    private static final String SELECT_ITEM_BASE = "SELECT * FROM ITEM i";
    private static final String SELECT_DETAILS_BASE = "SELECT * FROM ITEM_DETAILS i";
    private static final String IMAGE_JOIN = "INNER JOIN Image img ON i.image_id = img.id";
    private static final String CATEGORY_JOIN = "INNER JOIN item_category ON i.id = item_category.item_id";


    public ItemRepositoryImpl(PgPool client, ItemMapper itemMapper) {
        this.client = client;
        this.itemMapper = itemMapper;
    }

    @Override
    public Uni<List<Item>> getAllItems() {
        return this.client.preparedQuery(format("%s %s %s", SELECT_ITEM_BASE, IMAGE_JOIN, CATEGORY_JOIN))
                .execute()
                .map(itemMapper::getItems);
    }

    @Override
    public Uni<Item> getItemById(Long id) {
        return this.client.preparedQuery(format("%s %s WHERE i.id = $1", SELECT_ITEM_BASE, IMAGE_JOIN))
                .execute(Tuple.of(id))
                .map(rs -> {
                    if (isRowSetEmpty(rs)) {
                        return Item.builder().build();
                    }
                    return itemMapper.rowToItem(rs.iterator().next());
                });
    }

    @Override
    public Uni<List<Item>> getItemsListByIdList(List<Long> ids) {
        return this.client.preparedQuery(format("%s %s %s WHERE i.id = ANY ($1) ORDER BY i.id DESC",
                SELECT_ITEM_BASE, IMAGE_JOIN, CATEGORY_JOIN))
                .execute(Tuple.of(ids.toArray(new Long[ids.size()])))
                .map(itemMapper::getItems);
    }

    @Override
    public Uni<List<ItemDetails>> getAllItemDetails() {
        return this.client.preparedQuery(SELECT_DETAILS_BASE)
                .execute()
                .map(itemMapper::getItemDetails);
    }

    @Override
    public Uni<List<ItemDetails>> getItemDetailsListByItemId(Long id) {
        return this.client.preparedQuery(format("%s WHERE item_id = $1", SELECT_DETAILS_BASE))
                .execute(Tuple.of(id))
                .map(itemMapper::getItemDetails);
    }

    @Override
    public Uni<List<ItemDetails>> getItemDetailsListByIdList(List<Long> ids) {
        return this.client.preparedQuery(format("%s WHERE item_id = ANY ($1)", SELECT_DETAILS_BASE))
                .execute(Tuple.of(ids.toArray(new Long[ids.size()])))
                .map(itemMapper::getItemDetails);
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
                    if (isRowSetEmpty(rs)) {
                        return Item.builder().build();
                    }
                    return itemMapper.rowToItem(rs.iterator().next());
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
                    if (isRowSetEmpty(rs)) {
                        return ItemDetails.builder().build();
                    }
                    return itemMapper.rowToItemDetails(rs.iterator().next());
                });
    }

    @Override
    public Uni<Integer> getItemStock(Long id) {
        return this.client.preparedQuery("SELECT stock FROM ITEM WHERE id = $1")
                .execute(Tuple.of(id))
                .map(rs -> {
                    if (isRowSetEmpty(rs)) {
                        return -1;
                    }
                    return rs.iterator().next().getInteger("stock");
                });
    }

    @Override
    public Uni<Integer> changeItemStock(Long id, int stock) {
        return this.client.preparedQuery("UPDATE ITEM SET stock = $1 WHERE id = $2 RETURNING stock")
                .execute(Tuple.of(stock, id))
                .map(rs -> {
                    if (isRowSetEmpty(rs)) {
                        return -1;
                    }
                    return rs.iterator().next().getInteger("stock");
                });
    }
}
