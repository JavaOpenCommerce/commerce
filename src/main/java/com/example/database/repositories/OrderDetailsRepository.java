package com.example.database.repositories;

import com.example.database.entity.OrderDetails;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;

public class OrderDetailsRepository implements PanacheRepository<OrderDetails> {

    public List<OrderDetails> findOrderDetailsByUserId(Long id) {
        return list("user.id", id);
    }

}
