package com.example.javaopencommerce.catalog;

import com.example.javaopencommerce.catalog.dtos.FullItemDto;
import com.example.javaopencommerce.catalog.dtos.ItemDto;

import java.util.List;

class ItemQueryRepositoryImpl implements ItemQueryRepository {

    private final PsqlItemRepository psqlProductRepository;
    private final ItemDtoFactory dtoFactory;

    ItemQueryRepositoryImpl(PsqlItemRepository psqlProductRepository,
                            ItemDtoFactory dtoFactory) {
        this.psqlProductRepository = psqlProductRepository;
        this.dtoFactory = dtoFactory;
    }

    @Override
    public FullItemDto getItemById(Long id) {
        ItemEntity itemEntity = psqlProductRepository.getItemById(id);
        return dtoFactory.toFullProductDto(itemEntity);
    }

    @Override
    public List<FullItemDto> getAllFullItems() {
        List<ItemEntity> allItems = psqlProductRepository.getAllItems();
        return allItems.stream()
                .map(dtoFactory::toFullProductDto)
                .toList();
    }

    @Override
    public List<ItemDto> getShippingMethods() {
        List<ItemEntity> allShippingMethods = psqlProductRepository.getAllShippingMethods();
        return allShippingMethods.stream()
                .map(dtoFactory::productToDto)
                .toList();
    }

    @Override
    public List<ItemDto> getItemsByIdList(List<Long> ids) {
        List<ItemEntity> itemsList = psqlProductRepository.getItemsByIdList(ids);
        return itemsList.stream()
                .map(dtoFactory::productToDto)
                .toList();
    }
}