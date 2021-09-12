package com.example.database.repositories.impl;

import static com.example.utils.CommonRow.isRowSetEmpty;
import static java.time.LocalDate.now;

import com.example.database.repositories.impl.mappers.OrderDetailsMapper;
import com.example.javaopencommerce.order.OrderDetails;
import com.example.javaopencommerce.order.OrderDetailsRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class OrderDetailsRepositoryImpl implements OrderDetailsRepository {

    private final PgPool client;
    private final OrderDetailsMapper detailsMapper;

    public OrderDetailsRepositoryImpl(PgPool client, OrderDetailsMapper detailsMapper) {
        this.client = client;
        this.detailsMapper = detailsMapper;
    }

    @Override
    public Uni<List<OrderDetails>> findOrderDetailsByUserId(Long id) {
        return this.client.preparedQuery("SELECT * FROM ORDER_DETAILS od WHERE od.userentity_id = $1")
                .execute(Tuple.of(id))
                .map(this.detailsMapper::getOrderDetailsList);
    }

    @Override
    public Uni<OrderDetails> findOrderDetailsById(Long id) {
        return this.client.preparedQuery("SELECT * FROM ORDER_DETAILS od WHERE od.id = $1")
                .execute(Tuple.of(id))
                .map(rs -> {
                    if (isRowSetEmpty(rs)) {
                        return OrderDetails.builder().build();
                    }
                    return this.detailsMapper.rowToOrderDetails(rs.iterator().next());
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

        return this.client.preparedQuery("INSERT INTO ORDER_DETAILS (creation_date, order_status, payment_method, " +
                        "payment_status, address_id, user_id, order_details) " +
                        "VALUES ($1, $2, $3, $4, $5, $6, $7) " +
                        "RETURNING id, creation_date, order_status, payment_method, " +
                        "payment_status, address_id, user_id, productsjson")
                .execute(Tuple.tuple(List.of(args)))
                .map(rs -> {
                    if (isRowSetEmpty(rs)) {
                        return OrderDetails.builder().build();
                    }
                    return this.detailsMapper.rowToOrderDetails(rs.iterator().next());
                });
    }
}
