package com.example.opencommerce.adapters.database.warehouse.sql;

import com.example.opencommerce.adapters.database.EntityNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

@ApplicationScoped
class PsqlWarehouseItemRepositoryImpl implements PsqlWarehouseItemRepository {

    private final EntityManager em;

    public PsqlWarehouseItemRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public ItemStockEntity getItemById(Long id) {
        try {
            return em.createQuery("SELECT i FROM ItemStock i WHERE i.id = ?1", ItemStockEntity.class)
                    .setParameter(1, id)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Order", id);
        }
    }

    @Override
    public List<ItemStockEntity> getItemStocksByIdList(List<Long> ids) {
        return em.createQuery("SELECT i FROM ItemStock i WHERE i.id in(?1)", ItemStockEntity.class)
                .setParameter(1, ids)
                .getResultList();
    }

    @Override
    public void updateStock(Long id, Integer newStock) {
        int result = em.createQuery("UPDATE ItemStock i SET i.stock = ?1 WHERE i.id = ?2")
                .setParameter(1, newStock)
                .setParameter(2, id)
                .executeUpdate();

        if (result != 1) {
            throw new IllegalStateException("Failed to update stocks for item with id: " + id);
        }
    }

    @Override
    public void save(WarehouseItemEntity item) {
        em.merge(item);
    }

    @Override
    public void save(ItemStockEntity item) {
        em.merge(item);
    }
}
