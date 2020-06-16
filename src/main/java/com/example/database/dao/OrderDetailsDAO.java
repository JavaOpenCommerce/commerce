package com.example.database.dao;

import com.example.database.entity.ItemQuantity;
import com.example.database.entity.OrderDetails;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class OrderDetailsDAO implements DAO<OrderDetails> {


    private final EntityManager em;

    public OrderDetailsDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<OrderDetails> getById(Long id) {
        OrderDetails orderDetails = em.find(OrderDetails.class, id);
        return ofNullable(orderDetails);
    }

    @Override
    public List<OrderDetails> getAll() {
        return em.createQuery("SELECT od FROM OrderDetails od").getResultList();
    }

    @Override
    public OrderDetails save(OrderDetails orderDetails) {
        if (orderDetails.getId() == null) {
            em.persist(orderDetails);
            em.flush();
            return orderDetails;
        } else {
            return em.merge(orderDetails);
        }
    }

    @Override
    public void delete(OrderDetails orderDetails) {
        if (em.contains(orderDetails)) {
            em.remove(orderDetails);
        }
    }

    @Override
    public void deleteById(Long id) {
        em.createQuery("DELETE FROM OrderDetails od WHERE od.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    public List<ItemQuantity> getItemQuantitiesByOrderId(Long id) {
        return em.createQuery("SELECT iq FROM ItemQuantity iq WHERE iq.orderDetails.id = :id", ItemQuantity.class)
                .setParameter("id", id)
                .getResultList();
    }
}
