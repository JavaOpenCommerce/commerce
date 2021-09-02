package com.example.database.repositories.impl.mappers;

import com.example.database.entity.OrderDetails;
import com.example.database.entity.OrderStatus;
import com.example.database.entity.PaymentMethod;
import com.example.database.entity.PaymentStatus;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

import static com.example.database.entity.PaymentMethod.valueOf;
import static com.example.utils.CommonRow.isRowSetEmpty;
import static java.util.Collections.emptyList;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@ApplicationScoped
public class OrderDetailsMapper {

    private static final String ID = "id";
    private static final String CREATION_DATE = "creationdate";
    private static final String PAYMENT_METHOD = "paymentmethod";
    private static final String PAYMENT_STATUS = "paymentstatus";
    private static final String ORDER_STATUS = "orderstatus";
    private static final String ADDRESS_ID = "address_id";
    private static final String USER_ID = "userentity_id";
    private static final String PRODUCTS_JSON = "productsjson";

    public List<OrderDetails> getOrderDetailsList(RowSet<Row> rs) {
        if (isRowSetEmpty(rs)) {
            return emptyList();
        }

        return stream(rs.spliterator(), false)
                .map(this::rowToOrderDetails)
                .collect(toList());
    }

    public OrderDetails rowToOrderDetails(Row row) {
        if (row == null) {
            return OrderDetails.builder().build();
        }

        return OrderDetails.builder()
                .id(row.getLong(ID))
                .creationDate(row.getLocalDate(CREATION_DATE))
                .paymentMethod(of(valueOf(row.getString(PAYMENT_METHOD)))
                        .orElse(PaymentMethod.MONEY_TRANSFER))
                .paymentStatus(of(PaymentStatus.valueOf(row.getString(PAYMENT_STATUS)))
                        .orElse(PaymentStatus.BEFORE_PAYMENT))
                .orderStatus(of(OrderStatus.valueOf(row.getString(ORDER_STATUS)))
                        .orElse(OrderStatus.NEW))
                .shippingAddressId(row.getLong(ADDRESS_ID))
                .userEntityId(row.getLong(USER_ID))
                .productsJson(row.getString(PRODUCTS_JSON))
                .build();
    }
}
