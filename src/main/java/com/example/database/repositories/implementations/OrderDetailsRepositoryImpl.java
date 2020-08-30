package com.example.database.repositories.implementations;

import com.example.database.entity.*;
import com.example.database.repositories.interfaces.OrderDetailsRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.List;

import static com.example.database.entity.OrderStatus.NEW;
import static com.example.database.entity.PaymentMethod.*;
import static com.example.database.entity.PaymentStatus.BEFORE_PAYMENT;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@ApplicationScoped
public class OrderDetailsRepositoryImpl implements OrderDetailsRepository {

    private final PgPool client;

    public OrderDetailsRepositoryImpl(PgPool client) {
        this.client = client;
    }

    @Override
    public List<OrderDetails> findOrderDetailsByUserId(Long id) {
        //todo
        return emptyList();
    }

    @Override
    public Uni<OrderDetails> saveOrder(OrderDetails orderDetails) {
       return client.preparedQuery("INSERT INTO ORDERDETAILS (creationdate, orderstatus, paymentmethod, " +
                                        "paymentstatus, addres_id, userentity_id) " +
                                        "VALUES($1, $2, $3, $4, $5, $6)", Tuple.of(
                LocalDate.now(),
                orderDetails.getOrderStatus().toString(),
                orderDetails.getPaymentMethod().toString(),
                orderDetails.getPaymentStatus().toString(),
                orderDetails.getShippingAddress().getId(),
                orderDetails.getUserEntity().getId()
        )).onItem().apply(rs -> {
            if (rs == null || !rs.iterator().hasNext()) {
                return OrderDetails.builder().build();
            }
            return rowToOrderDetails(rs.iterator().next());
        });
    }

    //--Helpers-----------------------------------------------------------------------------------------------------

    private OrderDetails rowToOrderDetails(Row row) {
        if (row == null) {
            return OrderDetails.builder().build();
        }
        
        return OrderDetails.builder()
                .id(row.getLong("id"))
                .creationDate(row.getLocalDate("creationdate"))
                .paymentMethod(ofNullable(valueOf(row.getString("paymentmethod")))
                        .orElse(MONEY_TRANSFER))
                .paymentStatus(ofNullable(PaymentStatus.valueOf(row.getString("paymentstatus")))
                        .orElse(BEFORE_PAYMENT))
                .orderStatus(ofNullable(OrderStatus.valueOf(row.getString("orderstatus")))
                        .orElse(NEW))
                .shippingAddress(Address.builder().build())
                .products(emptyList())
                .userEntity(UserEntity.builder().build())
                .build();
    }
}
