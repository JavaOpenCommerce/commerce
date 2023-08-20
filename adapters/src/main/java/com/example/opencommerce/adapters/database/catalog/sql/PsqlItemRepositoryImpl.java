package com.example.opencommerce.adapters.database.catalog.sql;


import com.example.opencommerce.adapters.database.EntityNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@ApplicationScoped
class PsqlItemRepositoryImpl implements PsqlItemRepository {

    private final EntityManager em;

    public PsqlItemRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<ItemEntity> getAllItems() {
        return this.em.createQuery("SELECT i FROM Item i WHERE i.shipping = false", ItemEntity.class)
                .getResultList();
    }

    @Override
    public ItemEntity getItemById(Long id) {
        try {
            return this.em.createQuery("SELECT i FROM Item i WHERE i.id = ?1", ItemEntity.class)
                    .setParameter(1, id)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Item", id);
        }
    }

    @Override
    public List<ItemEntity> getItemsByIdList(List<Long> ids) {
        return this.em.createQuery("SELECT i FROM Item i WHERE i.id IN ?1", ItemEntity.class)
                .setParameter(1, ids)
                .getResultList();
    }

    @Override
    public List<ItemEntity> getAllShippingMethods() {
        return this.em.createQuery("SELECT i FROM Item i WHERE i.shipping = true", ItemEntity.class)
                .getResultList();
    }

    @Override
    public ItemEntity saveItem(ItemEntity item) {
        return em.merge(item);
    }

    @Override
    public Integer getItemStock(Long id) {
        try {
            return em.createQuery("SELECT i.stock FROM Item i WHERE i.id = ?1", Integer.class)
                    .setParameter(1, id)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Item", id);
        }
    }

    @Override
    public Integer changeItemStock(Long id, int stock) {
        // Poor solution TODO refactor
        return (Integer) this.em.createNativeQuery(
                        "UPDATE ITEM SET stock = ?1 WHERE id = ?2 RETURNING stock", Integer.class)
                .setParameter(1, stock)
                .setParameter(2, id)
                .getSingleResult();
    }
}
