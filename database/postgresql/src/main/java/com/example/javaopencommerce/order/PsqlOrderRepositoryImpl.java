package com.example.javaopencommerce.order;

import static com.example.javaopencommerce.CommonRow.isRowSetEmpty;
import static java.util.stream.Collectors.toList;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
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
  public Uni<OrderDetailsEntity> saveOrder(OrderDetailsEntity orderDetails,
      List<SimpleProductEntity> products) {
    return client.withTransaction(conn -> {

      Uni<Void> voidStockUni = Uni.combine().all().unis(
          products.stream()
              .map(prod -> updateSingleItemStock(conn, -prod.getAmount(), prod.getItemId()))
              .collect(toList()))
          .discardItems();

      Uni<OrderDetailsEntity> orderDetailsEntityUni = saveOrder(conn, orderDetails);

      return Uni.combine().all().unis(orderDetailsEntityUni, voidStockUni)
          .combinedWith((OrderDetailsEntity savedOrderDetails, Void stock) -> savedOrderDetails);
    });
  }

  private Uni<OrderDetailsEntity> saveOrder(SqlConnection conn, OrderDetailsEntity orderDetails) {
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

    return conn
        .preparedQuery("INSERT INTO ORDER_DETAILS (creation_date, order_status, payment_method, " +
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

  private Uni<RowSet<Row>> updateSingleItemStock(SqlConnection conn, int stockChange, Long id) {
    return conn
        .preparedQuery("UPDATE ITEM SET stock = stock + $1 WHERE id = $2")
        .execute(Tuple.of(stockChange, id));
  }
}
