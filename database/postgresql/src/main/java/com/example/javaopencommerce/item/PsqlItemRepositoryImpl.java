package com.example.javaopencommerce.item;

import static java.lang.String.format;

import com.example.javaopencommerce.CommonRow;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;
import java.util.List;

class PsqlItemRepositoryImpl implements PsqlItemRepository {

  private final PgPool client;
  private final ItemMapper itemMapper;

  private static final String SELECT_ITEM_BASE = "SELECT * FROM ITEM i";
  private static final String SELECT_DETAILS_BASE = "SELECT * FROM ITEM_DETAILS i";

  PsqlItemRepositoryImpl(PgPool client) {
    this.client = client;
    this.itemMapper = new ItemMapper();
  }

  @Override
  public Uni<List<ItemEntity>> getAllItems() {
    return this.client.preparedQuery(format("%s WHERE i.shipping = false", SELECT_ITEM_BASE))
        .execute()
        .map(this.itemMapper::getItems);
  }

  @Override
  public Uni<ItemEntity> getItemById(Long id) {
    return this.client.preparedQuery(format("%s WHERE i.id = $1", SELECT_ITEM_BASE))
        .execute(Tuple.of(id))
        .map(rs -> {
          if (CommonRow.isRowSetEmpty(rs)) {
            return ItemEntity.builder().build();
          }
          return this.itemMapper.rowToItem(rs.iterator().next());
        });
  }

  @Override
  public Uni<List<ItemEntity>> getItemsListByIdList(List<Long> ids) {
    return this.client.preparedQuery(format("%s WHERE i.id = ANY ($1) ORDER BY i.id DESC",
        SELECT_ITEM_BASE))
        .execute(Tuple.of(ids.toArray(new Long[ids.size()])))
        .map(this.itemMapper::getItems);
  }

  @Override
  public Uni<List<ItemEntity>> getAllShippingMethods() {
    return this.client.preparedQuery(
        format("%s WHERE i.shipping = true", SELECT_ITEM_BASE))
        .execute()
        .map(this.itemMapper::getItems);
  }

  @Override
  public Uni<List<ItemDetailsEntity>> getAllDetailsForShippingMethods() {
    return this.client.preparedQuery(
        format("%s INNER JOIN item it ON i.item_id = it.id WHERE it.shipping = true",
            SELECT_DETAILS_BASE))
        .execute()
        .map(this.itemMapper::getItemDetails);
  }


  @Override
  public Uni<List<ItemDetailsEntity>> getAllItemDetails() {
    return this.client.preparedQuery(
        format("%s INNER JOIN item it ON i.item_id = it.id WHERE it.shipping = false",
            SELECT_DETAILS_BASE))
        .execute()
        .map(this.itemMapper::getItemDetails);
  }

  @Override
  public Uni<List<ItemDetailsEntity>> getItemDetailsListByItemId(Long id) {
    return this.client.preparedQuery(format("%s WHERE item_id = $1", SELECT_DETAILS_BASE))
        .execute(Tuple.of(id))
        .map(this.itemMapper::getItemDetails);
  }

  @Override
  public Uni<List<ItemDetailsEntity>> getItemDetailsListByIdList(List<Long> ids) {
    return this.client.preparedQuery(format("%s WHERE item_id = ANY ($1)", SELECT_DETAILS_BASE))
        .execute(Tuple.of(ids.toArray(new Long[ids.size()])))
        .map(this.itemMapper::getItemDetails);
  }

  @Override
  public Uni<ItemEntity> saveItem(ItemEntity item) {
    return this.client.preparedQuery("INSERT INTO ITEM (stock, valuegross, vat, image_id) " +
        "VALUES($1, $2, $3, $4)")
        .execute(Tuple.of(
            item.getStock(),
            item.getValueGross(),
            item.getVat(),
            item.getImageId())
        ).map(rs -> {
          if (CommonRow.isRowSetEmpty(rs)) {
            return ItemEntity.builder().build();
          }
          return this.itemMapper.rowToItem(rs.iterator().next());
        });
  }

  @Override
  public Uni<ItemDetailsEntity> saveItemDetails(ItemDetailsEntity itemDetails) {
    return this.client.preparedQuery("INSERT INTO ITEMDETAILS (description, lang, name, item_id) " +
        "VALUES($1, $2, $3, $4)")
        .execute(Tuple.of(
            itemDetails.getDescription(),
            itemDetails.getLang().toLanguageTag(),
            itemDetails.getName(),
            itemDetails.getItemId()
        )).map(rs -> {
          if (CommonRow.isRowSetEmpty(rs)) {
            return ItemDetailsEntity.builder().build();
          }
          return this.itemMapper.rowToItemDetails(rs.iterator().next());
        });
  }

  @Override
  public Uni<Integer> getItemStock(Long id) {
    return this.client.preparedQuery("SELECT stock FROM ITEM WHERE id = $1")
        .execute(Tuple.of(id))
        .map(rs -> {
          if (CommonRow.isRowSetEmpty(rs)) {
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
          if (CommonRow.isRowSetEmpty(rs)) {
            return -1;
          }
          return rs.iterator().next().getInteger("stock");
        });
  }
}
