package com.example.database.dao;

import com.example.database.entity.Item;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@ApplicationScoped
public class ItemDAOImpl implements ItemDAO {

    private final EntityManager em;

    public ItemDAOImpl(EntityManager em) {this.em = em;}

    @Override
    public Optional<Item> getById(Long id) {
        Item item = em.find(Item.class, id);
        return ofNullable(item);
    }

    @Override
    public List<Item> getAll() {
        return em.createQuery("SELECT i FROM Item i", Item.class)
                .getResultList();
    }

    @Override
    public Item save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
            em.flush();
            return item;
        } else {
            return em.merge(item);
        }
    }

    @Override
    public void delete(Item item) {
        if (em.contains(item)) {
            em.remove(item);
        }
    }

    @Override
    public void deleteById(Long id) {
        em.createQuery("DELETE FROM Item i WHERE i.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public List<Item> searchItemByName(String query) {
        return em.createQuery("SELECT i FROM Item i WHERE i.name LIKE :query")
                .setParameter("query","%" + query.trim() + "%")
                .getResultList();
    }

    @Override
    public List<Item> listItemByCategoryId(Long categoryId) {
        return em.createQuery("SELECT i FROM Item i INNER JOIN i.category c WHERE c.id = :categoryId")
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    public boolean validateShippingMethod(Long id) {
        return em.createQuery("SELECT 1 FROM Item i INNER JOIN Category c WHERE c.categoryName = 'Shipping' AND i.id = :id")
                .setParameter("id", id)
                .getSingleResult() != null;
    }

    @Override
    public List<Item> listItemByProducerId(Long producerId) {
        return em.createQuery("SELECT i FROM Item i WHERE i.producer.id = :producerId")
                .setParameter("producerId", producerId)
                .getResultList();
    }

    @Override
    public int getItemStock(Long id) {
        return em.createQuery("SELECT i.stock FROM Item i WHERE i.id = :id", Integer.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public boolean exists(Long id) {
        return em.createQuery("SELECT 1 FROM Item i WHERE i.id = :id", Item.class)
                .setParameter("id", id)
                .getSingleResult() != null;
    }
}
