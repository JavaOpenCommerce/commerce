package com.example.database.repositories.implementations;

import com.example.database.entity.ItemQuantity;
import com.example.database.repositories.interfaces.ItemQuantityRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

import static java.util.Collections.emptyList;

@ApplicationScoped
public class ItemQuantityRepositoryImpl implements ItemQuantityRepository {

    private final PgPool client;

    public ItemQuantityRepositoryImpl(PgPool client) {
        this.client = client;
    }

    @Override
    public List<ItemQuantity> getItemQuantitiesByOrderId(Long id) {
        //todo
        return emptyList();
    }

    @Override
    public Uni<ItemQuantity> saveItemQuantity(ItemQuantity itemQuantity) {
        return client.preparedQuery("INSERT INTO ITEMQUANTITY (amount, item_id, order_id) " +
                                        "VALUES($1, $2, $3)", Tuple.of(
                                                itemQuantity.getAmount(),
                                                itemQuantity.getItemId(),
                                                itemQuantity.getOrderId()))
                .onItem().apply(rs -> {
            if (rs == null || !rs.iterator().hasNext()) {
                return ItemQuantity.builder().build();
            }
            return rowToItemQuantity(rs.iterator().next());
        });
    }

    //--Helpers-----------------------------------------------------------------------------------------------------

    private ItemQuantity rowToItemQuantity(Row row) {
        if (row == null) {
            return ItemQuantity.builder().build();
        }

        return ItemQuantity.builder()
                .id(row.getLong("id"))
                .amount(row.getInteger("amount"))
                .itemId(row.getLong("item_id"))
                .orderId(row.getLong("order_id"))
                .build();
    }
}
