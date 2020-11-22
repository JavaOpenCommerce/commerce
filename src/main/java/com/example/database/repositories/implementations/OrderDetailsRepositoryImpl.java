package com.example.database.repositories.implementations;

import com.example.database.entity.OrderDetails;
import com.example.database.entity.OrderStatus;
import com.example.database.entity.PaymentStatus;
import com.example.database.repositories.interfaces.OrderDetailsRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

import static com.example.database.entity.OrderStatus.NEW;
import static com.example.database.entity.PaymentMethod.MONEY_TRANSFER;
import static com.example.database.entity.PaymentMethod.valueOf;
import static com.example.database.entity.PaymentStatus.BEFORE_PAYMENT;
import static java.time.LocalDate.now;
import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@ApplicationScoped
public class OrderDetailsRepositoryImpl implements OrderDetailsRepository {

    private final PgPool client;

    public OrderDetailsRepositoryImpl(PgPool client) {
        this.client = client;
    }

    @Override
    public Uni<List<OrderDetails>> findOrderDetailsByUserId(Long id) {
        return this.client.preparedQuery("SELECT * FROM ORDERDETAILS od WHERE od.userentity_id = $1")
                .execute(Tuple.of(id))
                .onItem().apply(this::getOrderDetailsList);
    }

    @Override
    public Uni<OrderDetails> findOrderDetailsById(Long id) {
        return this.client.preparedQuery("SELECT * FROM ORDERDETAILS od WHERE od.id = $1")
                .execute(Tuple.of(id))
                .onItem().apply(rs -> {
                    if (isRowSetEmpty(rs)) {
                        return OrderDetails.builder().build();
                    }
                    return rowToOrderDetails(rs.iterator().next());
                });
    }

    @Override
    public Uni<OrderDetails> saveOrder(OrderDetails orderDetails) {
        Object[] args = {
                now(),
                orderDetails.getOrderStatus().toString(),
                orderDetails.getPaymentMethod().toString(),
                orderDetails.getPaymentStatus().toString(),
                orderDetails.getShippingAddressId(),
                orderDetails.getUserEntityId(),
                orderDetails.getProductsJson()
        };
        return this.client.preparedQuery("INSERT INTO ORDERDETAILS (creationdate, orderstatus, paymentmethod, " +
                                        "paymentstatus, address_id, userentity_id, productsjson) " +
                                        "VALUES($1, $2, $3, $4, $5, $6, $7)")
                .execute(Tuple.of(args))
                .onItem().apply(rs -> {
                    if (isRowSetEmpty(rs)) {
                        return OrderDetails.builder().build();
                    }
                    return rowToOrderDetails(rs.iterator().next());
                });
    }

    //--Helpers-----------------------------------------------------------------------------------------------------

    private boolean isRowSetEmpty(RowSet<Row> rs) {
        return isNull(rs) || !rs.iterator().hasNext();
    }

    private List<OrderDetails> getOrderDetailsList(RowSet<Row> rs) {
        if (isRowSetEmpty(rs)) {
            return emptyList();
        }

        return stream(rs.spliterator(), false)
                .map(this::rowToOrderDetails)
                .collect(toList());
    }

    private OrderDetails rowToOrderDetails(Row row) {
        if (row == null) {
            return OrderDetails.builder().build();
        }
        
        return OrderDetails.builder()
                .id(row.getLong("id"))
                .creationDate(row.getLocalDate("creationdate"))
                .paymentMethod(of(valueOf(row.getString("paymentmethod")))
                        .orElse(MONEY_TRANSFER))
                .paymentStatus(of(PaymentStatus.valueOf(row.getString("paymentstatus")))
                        .orElse(BEFORE_PAYMENT))
                .orderStatus(of(OrderStatus.valueOf(row.getString("orderstatus")))
                        .orElse(NEW))
                .shippingAddressId(row.getLong("address_id"))
                .userEntityId(row.getLong("userentity_id"))
                .productsJson(row.getString("productsjson"))
                .build();
    }
}
