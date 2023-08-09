package com.example.javaopencommerce.warehouse;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.ItemId;
import com.example.javaopencommerce.warehouse.query.ItemStockDto;
import com.example.javaopencommerce.warehouse.query.WarehouseQueryRepository;

import javax.enterprise.context.ApplicationScoped;
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
