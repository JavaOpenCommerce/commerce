package com.example.database.dao;

import com.example.database.entity.ItemQuantity;
import com.example.database.entity.OrderDetails;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@ApplicationScoped
public class OrderDetailsDAOImpl implements OrderDetailsDAO {


    private final EntityManager em;

    public OrderDetailsDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<OrderDetails> getById(Long id) {
        OrderDetails orderDetails = em.find(OrderDetails.class, id);
        return ofNullable(orderDetails);
    }

    @Override
    public List<OrderDetails> getAll() {
        return em.createQuery("SELECT od FROM OrderDetails od", OrderDetails.class)
                .getResultList();
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

    //item quantities section

    @Override
    public List<ItemQuantity> getItemQuantitiesByOrderId(Long id) {
        return em.createQuery("SELECT iq FROM ItemQuantity iq WHERE iq.orderDetails.id = :id", ItemQuantity.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public ItemQuantity addNewItemQuantityToOrder(ItemQuantity itemQuantity) {
        if (itemQuantity.getId() == null) {
            em.persist(itemQuantity);
            em.flush();
            return itemQuantity;
        } else {
            return em.merge(itemQuantity);
        }
    }

    @Override
    public void deleteItemQuantityById(Long id) {
        em.createQuery("DELETE FROM ItemQuantity iq WHERE iq.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
