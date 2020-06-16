package com.example.database.dao;

import com.example.database.entity.Item;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@ApplicationScoped
public class ItemDAO implements DAO<Item> {

    private final EntityManager em;

    public ItemDAO(EntityManager em) {this.em = em;}

    @Override
    public Optional<Item> getById(Long id) {
        Item item = em.find(Item.class, id);
        return ofNullable(item);
    }

    @Override
    public List<Item> getAll() {
        return em.createQuery("SELECT i FROM Item i")
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
}
