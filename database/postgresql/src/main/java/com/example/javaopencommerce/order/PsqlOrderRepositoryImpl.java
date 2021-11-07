package com.example.javaopencommerce.order;

import static com.example.javaopencommerce.CommonRow.isRowSetEmpty;
import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.exception.EntityNotFoundException;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import java.util.List;


class PsqlOrderRepositoryImpl implements PsqlOrderRepository {

  private final PgPool client;
  private final OrderMapper detailsMapper;

  public PsqlOrderRepositoryImpl(PgPool client) {
    this.client = client;
    this.detailsMapper = new OrderMapper();
  }

  @Override
  public Uni<List<OrderEntity>> findOrderByUserId(Long id) {
    return this.client.preparedQuery("SELECT * FROM ORDER_DETAILS od WHERE od.userentity_id = $1")
        .execute(Tuple.of(id))
        .map(this.detailsMapper::getOrderEntityList);
  }

  @Override
  public Uni<OrderEntity> findOrderById(Long id) {
    return this.client.preparedQuery("SELECT * FROM ORDER_DETAILS od WHERE od.id = $1")
        .execute(Tuple.of(id))
        .map(rs -> {
          if (isRowSetEmpty(rs)) {
            throw new EntityNotFoundException("Order", id);
          }
          return this.detailsMapper.rowToOrder(rs.iterator().next());
        });
  }

  @Override
  public Uni<OrderEntity> saveOrder(OrderEntity order,
      List<SimpleProductEntity> products) {
    return client.withTransaction(conn -> {

      Uni<Void> voidStockUni = Uni.combine().all().unis(
          products.stream()
              .map(prod -> updateSingleItemStock(conn, -prod.getAmount(), prod.getItemId()))
              .collect(toList()))
          .discardItems();

      Uni<OrderEntity> orderEntityUni = saveOrder(conn, order);

      return Uni.combine().all().unis(orderEntityUni, voidStockUni)
          .combinedWith((OrderEntity savedorder, Void stock) -> savedorder);
    });
  }

  private Uni<OrderEntity> saveOrder(SqlConnection conn, OrderEntity order) {
    Object[] args = {
        order.getCreationDate(),
        order.getOrderStatus(),
        order.getPaymentMethod(),
        order.getPaymentStatus(),
        order.getValueGross(),
        order.getValueNett(),
        order.getShippingAddressId(),
        order.getUserEntityId(),
        order.getSimpleProductsJson()
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
            return OrderEntity.builder().build();
          }
          return this.detailsMapper.rowToOrder(rs.iterator().next());
        });
  }

  private Uni<RowSet<Row>> updateSingleItemStock(SqlConnection conn, int stockChange, Long id) {
    return conn
        .preparedQuery("UPDATE ITEM SET stock = stock + $1 WHERE id = $2")
        .execute(Tuple.of(stockChange, id));
  }
}
