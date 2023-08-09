package com.example.opencommerce.adapters.database.catalog.sql;


import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

@ApplicationScoped
class PsqlItemFamilyRepositoryImpl implements PsqlItemFamilyRepository {

    private final EntityManager em;

    public PsqlItemFamilyRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<ItemFamily> findFamilyForItemWithId(Long id) {
        try {
            return Optional.of(
                    this.em.createQuery(
                                    "SELECT if FROM ItemFamily if join if.family f WHERE f.itemId = ?1", ItemFamily.class)
                            .setParameter(1, id)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
