package com.example.database.dao;

import com.example.database.entity.Category;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@ApplicationScoped
public class CategoryDAOImpl implements CategoryDAO {

    private final EntityManager em;

    public CategoryDAOImpl(EntityManager em) {this.em = em;}


    @Override
    public Optional<Category> getById(Long id) {
        Category category = em.find(Category.class, id);
        return ofNullable(category);
    }

    @Override
    public List<Category> getAll() {
        return em.createQuery("SELECT c FROM Category c", Category.class)
                .getResultList();
    }

    @Override
    public Category save(Category category) {
        if (category.getId() == null) {
            em.persist(category);
            em.flush();
            return category;
        } else {
            return em.merge(category);
        }
    }

    @Override
    public void delete(Category category) {
        if (em.contains(category)) {
            em.remove(category);
        }
    }

    @Override
    public void deleteById(Long id) {
        em.createQuery("DELETE FROM Category c WHERE c.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
