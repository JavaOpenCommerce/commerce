package com.example.opencommerce.adapters.database.pricing.sql;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
class PsqlPriceEventRepositoryImpl implements PsqlPriceEventRepository {

    private final EntityManager em;

    public PsqlPriceEventRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<PriceEventEntity> getPriceEventsById(Long id) {
        return this.em.createQuery("SELECT e FROM PriceEvents e WHERE e.itemId = ?1", PriceEventEntity.class)
                .setParameter(1, id)
                .getResultList();
    }

    @Override
    public void savePriceEvent(PriceEventEntity entity) {
        this.em.merge(entity);
    }
}
