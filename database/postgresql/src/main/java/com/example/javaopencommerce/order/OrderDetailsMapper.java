package com.example.javaopencommerce.order;

import static java.util.Collections.emptyList;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

import com.example.javaopencommerce.CommonRow;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import java.util.List;

class OrderDetailsMapper {

    private static final String ID = "id";
    private static final String CREATION_DATE = "creation_date";
    private static final String PAYMENT_METHOD = "payment_method";
    private static final String PAYMENT_STATUS = "payment_status";
    private static final String VALUE_GROSS = "value_gross";
    private static final String VALUE_NETT = "value_nett";

    private static final String ORDER_STATUS = "order_status";
    private static final String ADDRESS_ID = "address_id";
    private static final String USER_ID = "user_id";
    private static final String ORDER_DETAILS = "order_details";

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
                .paymentMethod(of(row.getString(PAYMENT_METHOD))
                    .orElse(PaymentMethod.MONEY_TRANSFER.toString()))
                .paymentStatus(of(row.getString(PAYMENT_STATUS))
                        .orElse(PaymentStatus.BEFORE_PAYMENT.toString()))
                .orderStatus(of(row.getString(ORDER_STATUS))
                        .orElse(OrderStatus.NEW.toString()))
                .valueNett(row.getBigDecimal(VALUE_NETT))
                .valueGross(row.getBigDecimal(VALUE_GROSS))
                .shippingAddressId(row.getLong(ADDRESS_ID))
                .userEntityId(row.getLong(USER_ID))
                .simpleProductsJson(row.getString(ORDER_DETAILS))
                .build();
    }
}
