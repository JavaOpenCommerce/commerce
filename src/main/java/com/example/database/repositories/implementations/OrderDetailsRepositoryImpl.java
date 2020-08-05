package com.example.database.repositories.implementations;

import com.example.database.entity.OrderDetails;
import com.example.database.repositories.interfaces.OrderDetailsRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class OrderDetailsRepositoryImpl implements OrderDetailsRepository {

    @Override
    public List<OrderDetails> findOrderDetailsByUserId(Long id) {

        //todo
        return null;
    }
}
