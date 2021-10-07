package com.example.javaopencommerce.order;

import static com.example.javaopencommerce.CommonRow.isRowSetEmpty;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;
import java.util.List;


class PsqlOrderRepositoryImpl implements PsqlOrderRepository {

    private final PgPool client;
    private final OrderDetailsMapper detailsMapper;

    public PsqlOrderRepositoryImpl(PgPool client) {
        this.client = client;
        this.detailsMapper = new OrderDetailsMapper();
    }

    @Override
    public Uni<List<OrderDetailsEntity>> findOrderDetailsByUserId(Long id) {
        return this.client.preparedQuery("SELECT * FROM ORDER_DETAILS od WHERE od.userentity_id = $1")
                .execute(Tuple.of(id))
                .map(this.detailsMapper::getOrderDetailsList);
    }

    @Override
    public Uni<OrderDetailsEntity> findOrderDetailsById(Long id) {
        return this.client.preparedQuery("SELECT * FROM ORDER_DETAILS od WHERE od.id = $1")
                .execute(Tuple.of(id))
                .map(rs -> {
                    if (isRowSetEmpty(rs)) {
                        return OrderDetailsEntity.builder().build();
                    }
                    return this.detailsMapper.rowToOrderDetails(rs.iterator().next());
                });
    }

    @Override
    public Uni<OrderDetailsEntity> saveOrder(OrderDetailsEntity orderDetails) {
        Object[] args = {
              orderDetails.getCreationDate(),
              orderDetails.getOrderStatus(),
              orderDetails.getPaymentMethod(),
              orderDetails.getPaymentStatus(),
              orderDetails.getValueGross(),
              orderDetails.getValueNett(),
              orderDetails.getShippingAddressId(),
              orderDetails.getUserEntityId(),
              orderDetails.getSimpleProductsJson()
        };

        return this.client.preparedQuery("INSERT INTO ORDER_DETAILS (creation_date, order_status, payment_method, " +
                        "payment_status, value_gross, value_nett, address_id, user_id, order_details) " +
                        "VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9) " +
                        "RETURNING id, creation_date, order_status, payment_method, " +
                        "payment_status, value_gross, value_nett, address_id, user_id, order_details")
                .execute(Tuple.tuple(List.of(args)))
                .onItem()
                .transform(rs -> {
                    if (isRowSetEmpty(rs)) {
                        return OrderDetailsEntity.builder().build();
                    }
                    return this.detailsMapper.rowToOrderDetails(rs.iterator().next());
                });
    }
}
