package com.example.javaopencommerce.order;

import com.example.javaopencommerce.order.dtos.OrderDto;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
class OrderQueryRepositoryImpl implements OrderQueryRepository {

    private final PsqlOrderRepository psqlOrderRepository;

    OrderQueryRepositoryImpl(PsqlOrderRepository psqlOrderRepository) {
        this.psqlOrderRepository = psqlOrderRepository;
    }

    @Override
    public OrderDto findOrderById(UUID id) {
        OrderEntity order = psqlOrderRepository.findOrderById(id);
        return QueryOrderMapper.toQuery(order);
    }
}
