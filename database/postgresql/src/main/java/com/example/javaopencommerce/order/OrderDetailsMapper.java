package com.example.javaopencommerce.order;

import com.example.javaopencommerce.utils.CommonRow;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;

import java.util.List;

import static com.example.javaopencommerce.order.PaymentMethod.valueOf;
import static java.util.Collections.emptyList;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

public class OrderDetailsMapper {

    private static final String ID = "id";
    private static final String CREATION_DATE = "creationdate";
    private static final String PAYMENT_METHOD = "paymentmethod";
    private static final String PAYMENT_STATUS = "paymentstatus";
    private static final String ORDER_STATUS = "orderstatus";
    private static final String ADDRESS_ID = "address_id";
    private static final String USER_ID = "userentity_id";
    private static final String PRODUCTS_JSON = "productsjson";

    public List<OrderDetailsEntity> getOrderDetailsList(RowSet<Row> rs) {
        if (CommonRow.isRowSetEmpty(rs)) {
            return emptyList();
        }

        return stream(rs.spliterator(), false)
                .map(this::rowToOrderDetails)
                .collect(toList());
    }

    public OrderDetailsEntity rowToOrderDetails(Row row) {
        if (row == null) {
            return OrderDetailsEntity.builder().build();
        }

        return OrderDetailsEntity.builder()
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
