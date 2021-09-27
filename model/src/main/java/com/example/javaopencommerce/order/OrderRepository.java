package com.example.javaopencommerce.order;

import io.smallrye.mutiny.Uni;
import java.util.List;

interface OrderRepository {

  Uni<List<OrderDetails>> findOrderDetailsByUserId(Long id);

  Uni<OrderDetails> findOrderDetailsById(Long id);

  Uni<OrderDetails> saveOrder(OrderDetails orderDetails);

}
