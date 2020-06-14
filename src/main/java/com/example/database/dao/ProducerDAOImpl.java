package com.example.database.dao;

import com.example.database.entity.Producer;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@ApplicationScoped
public class ProducerDAOImpl implements ProducerDAO {

    private final EntityManager em;

    public ProducerDAOImpl(EntityManager em) {this.em = em;}


    @Override
    public Optional<Producer> getById(Long id) {
        Producer producer = em.find(Producer.class, id);
        return ofNullable(producer);
    }

    @Override
    public List<Producer> getAll() {
        return em.createQuery("SELECT p FROM Producer p", Producer.class)
                .getResultList();
    }

    @Override
    public Producer save(Producer producer) {
        if (producer.getId() == null) {
            em.persist(producer);
            em.flush();
            return producer;
        } else {
            return em.merge(producer);
        }
    }

    @Override
    public void delete(Producer producer) {
        em.remove(producer);
    }

    @Override
    public void deleteById(Long id) {
        em.createQuery("DELETE FROM Producer p WHERE p.id = :id", Producer.class)
                .setParameter("id", id)
                .executeUpdate();
    }
}
