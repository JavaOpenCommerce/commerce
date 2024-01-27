package com.example.opencommerce.adapters.database.warehouse.sql;

import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.warehouse.ItemStock;
import com.example.opencommerce.domain.warehouse.WarehouseItemRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;


@ApplicationScoped
class WarehouseItemRepositoryImpl implements WarehouseItemRepository {

    private final PsqlWarehouseItemRepository repository;
    private final ItemMapper mapper;

    public WarehouseItemRepositoryImpl(PsqlWarehouseItemRepository repository, ItemMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ItemStock getItemById(ItemId id) {
        return mapper.toModel(repository.getItemById(id.asLong()));
    }

    @Override
    @Transactional
    public void updateStock(ItemStock item) {
        repository.updateStock(item.id()
                .asLong(), item.quantityOnHand()
                .asInteger());
    }

    @Override
    public void save(ItemStock item) {
        repository.save(mapper.toEntity(item));
    }
}
