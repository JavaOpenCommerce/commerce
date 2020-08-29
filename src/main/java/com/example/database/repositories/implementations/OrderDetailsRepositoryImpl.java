package com.example.database.repositories.implementations;

import com.example.database.entity.OrderDetails;
import com.example.database.repositories.interfaces.OrderDetailsRepository;
import io.vertx.mutiny.pgclient.PgPool;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class OrderDetailsRepositoryImpl implements OrderDetailsRepository {

    private final PgPool client;

    public OrderDetailsRepositoryImpl(PgPool client) {
        this.client = client;
    }

    @Override
    public List<OrderDetails> findOrderDetailsByUserId(Long id) {

        //todo
        return null;
    }

    @Override
    public OrderDetails saveOrder(OrderDetails orderDetails) {


        return null;
    }
}
