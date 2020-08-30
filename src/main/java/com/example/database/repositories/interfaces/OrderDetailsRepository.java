package com.example.database.repositories.interfaces;

import com.example.database.entity.OrderDetails;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface OrderDetailsRepository {

    List<OrderDetails> findOrderDetailsByUserId(Long id);
    Uni<OrderDetails> saveOrder(OrderDetails orderDetails);

}
