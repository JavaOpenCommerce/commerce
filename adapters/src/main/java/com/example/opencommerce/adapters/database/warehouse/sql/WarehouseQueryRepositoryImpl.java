package com.example.opencommerce.adapters.database.warehouse.sql;

import com.example.opencommerce.app.warehouse.query.ItemStockDto;
import com.example.opencommerce.app.warehouse.query.WarehouseQueryRepository;
import com.example.opencommerce.domain.Amount;
import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.warehouse.ItemStock;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
class WarehouseQueryRepositoryImpl implements WarehouseQueryRepository {

    private final PsqlWarehouseItemRepository repository;
    private final ItemMapper mapper;

    public WarehouseQueryRepositoryImpl(PsqlWarehouseItemRepository repository, ItemMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public ItemStockDto getItemStockById(ItemId id) {
        ItemStockEntity item = repository.getItemById(id.asLong());
        return mapper.toDto(item);
    }

    @Override
    public Map<ItemId, Amount> getAvailableStocksByItemIds(List<ItemId> ids) {
        return repository.getItemStocksByIdList(ids.stream()
                        .map(ItemId::asLong)
                        .toList())
                .stream()
                .map(mapper::toModel)
                .collect(Collectors.toMap(ItemStock::id, ItemStock::freeStock));
    }

    @Override
    public Amount getAvailableStockById(ItemId id) {
        ItemStockEntity itemEntity = repository.getItemById(id.asLong());
        ItemStock model = mapper.toModel(itemEntity);
        return model.freeStock();
    }
}
