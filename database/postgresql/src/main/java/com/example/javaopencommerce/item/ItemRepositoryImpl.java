package com.example.javaopencommerce.item;

import io.smallrye.mutiny.Uni;
import java.util.List;

class ItemRepositoryImpl implements ItemRepository {

    private final PsqlItemRepository repository;

    ItemRepositoryImpl(PsqlItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public Uni<List<Item>> getAllItems() {
        Uni<List<ItemEntity>> items = repository.getAllItems();
        Uni<List<ItemDetailsEntity>> details = repository.getAllItemDetails();

        return Uni.combine().all().unis(items, details)
            .combinedWith(ItemDetailsMatcher::convertToItemModelList);
    }

    @Override
    public Uni<Item> getItemById(Long id) {
        Uni<List<ItemDetailsEntity>> details = repository.getItemDetailsListByItemId(id);
        Uni<ItemEntity> item = repository.getItemById(id);
        return Uni.combine().all()
            .unis(item, details)
            .combinedWith((it, det) -> {
                it.setDetails(det);
                return it.toItemModel();
            });
    }

    @Override
    public Uni<List<Item>> getItemsByIdList(List<Long> ids) {
        Uni<List<ItemDetailsEntity>> details = repository.getItemDetailsListByIdList(ids);
        Uni<List<ItemEntity>> items = repository.getItemsByIdList(ids);

        return Uni.combine().all().unis(items, details)
            .combinedWith(ItemDetailsMatcher::convertToItemModelList);
    }

    @Override
    public Uni<Item> saveItem(Item item) {
        return repository.saveItem(ItemEntity.fromSnapshot(item.getSnapshot()))
            .map(ItemEntity::toItemModel);
    }

    @Override
    public Uni<ItemDetails> saveItemDetails(Item itemDetails) {
        return null;
    }

    @Override
    public Uni<Integer> getItemStock(Long id) {
        return repository.getItemStock(id);
    }

    @Override
    public Uni<Integer> changeItemStock(Long id, int stock) {
        return repository.changeItemStock(id, stock);
    }
}
