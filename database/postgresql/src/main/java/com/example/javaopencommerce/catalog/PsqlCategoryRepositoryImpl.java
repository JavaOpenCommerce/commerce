package com.example.javaopencommerce.catalog;

import com.example.javaopencommerce.exception.EntityNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;


@ApplicationScoped
class PsqlCategoryRepositoryImpl implements PsqlCategoryRepository {

    private final EntityManager em;

    PsqlCategoryRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public String getCatalog() {
        try {
            return (String) em.createNativeQuery("SELECT body FROM category WHERE id = 1 LIMIT 1")
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Catalog");
        }
    }

    @Override
    public String saveCatalog(String catalog) {
        return (String) em.createNativeQuery(
                        "UPDATE category SET body = ?1 WHERE id = 1 RETURNING body")
                .getSingleResult();
    }
}
