package com.example.javaopencommerce.order;

import com.example.javaopencommerce.exception.EntityNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.UUID;


@ApplicationScoped
class PsqlOrderRepositoryImpl implements PsqlOrderRepository {

    private final EntityManager em;

    public PsqlOrderRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<OrderEntity> findOrderByUserId(UUID id) {
        return em.createQuery("SELECT o FROM OrderEntity o WHERE o.userId = ?1", OrderEntity.class)
                .setParameter(1, id.toString())
                .getResultList();
    }

    @Override
    public OrderEntity findOrderById(UUID id) {
        try {
            return em.createQuery("SELECT o FROM OrderEntity o WHERE o.id = ?1", OrderEntity.class)
                    .setParameter(1, id)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Order", id);
        }
    }

    @Override
    public OrderEntity saveOrder(OrderEntity order) {
        return em.merge(order);
    }
}
