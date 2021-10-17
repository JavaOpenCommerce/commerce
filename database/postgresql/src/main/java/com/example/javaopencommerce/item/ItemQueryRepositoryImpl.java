package com.example.javaopencommerce.item;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.item.dtos.ItemDetailsDto;
import com.example.javaopencommerce.item.dtos.ItemDto;
import io.smallrye.mutiny.Uni;
import java.util.List;

class ItemQueryRepositoryImpl implements ItemQueryRepository {

  private final PsqlItemRepository psqlItemRepository;
  private final ItemDetailsLangResolver resolver;
  private final ItemDtoFactory dtoFactory;

  ItemQueryRepositoryImpl(PsqlItemRepository psqlItemRepository,
      ItemDetailsLangResolver resolver, ItemDtoFactory dtoFactory) {
    this.psqlItemRepository = psqlItemRepository;
    this.resolver = resolver;
    this.dtoFactory = dtoFactory;
  }

  @Override
  public Uni<ItemDetailsDto> getItemById(Long id) {
    Uni<List<ItemDetailsEntity>> itemDetails = psqlItemRepository.getItemDetailsListByItemId(id);
    Uni<ItemEntity> itemEntity = psqlItemRepository.getItemById(id);

    return Uni.combine().all().unis(itemEntity, itemDetails)
        .combinedWith((item, details) -> ItemDetailsMatcher
            .convertToItemModelList(singletonList(item), details))
        .map(items ->
            items.stream()
                .map(Item::getSnapshot)
                .map(dtoFactory::itemToDetailsDto)
                .findFirst()
                .orElseThrow(RuntimeException::new));
  }

  @Override
  public Uni<List<ItemDto>> getAllItems() {
    Uni<List<ItemEntity>> allItems = psqlItemRepository.getAllItems();
    Uni<List<ItemDetailsEntity>> allItemDetails = psqlItemRepository.getAllItemDetails();
    return Uni.combine().all().unis(allItems, allItemDetails)
        .combinedWith(ItemDetailsMatcher::convertToItemModelList)
        .map(items -> items.stream().map(this::toDto).collect(toList()));
  }

  @Override
  public Uni<List<ItemDto>> getShippingMethods() {
    Uni<List<ItemEntity>> allShippingMethods = psqlItemRepository.getAllShippingMethods();
    Uni<List<ItemDetailsEntity>> allDetailsForShippingMethods = psqlItemRepository
        .getAllDetailsForShippingMethods();
    return Uni.combine().all().unis(allShippingMethods, allDetailsForShippingMethods)
        .combinedWith(ItemDetailsMatcher::convertToItemModelList)
        .map(items -> items.stream().map(this::toDto).collect(toList()));
  }

  @Override
  public Uni<List<ItemDto>> getItemsListByIdList(List<Long> ids) {
    Uni<List<ItemEntity>> itemsList = psqlItemRepository.getItemsListByIdList(ids);
    Uni<List<ItemDetailsEntity>> itemDetailsList = psqlItemRepository
        .getItemDetailsListByIdList(ids);

    return Uni.combine().all().unis(itemsList, itemDetailsList)
        .combinedWith(ItemDetailsMatcher::convertToItemModelList)
        .map(items -> items.stream().map(this::toDto).collect(toList()));
  }

  private ItemDto toDto(Item item) {
    ItemSnapshot itemSnapshot = item.getSnapshot();
    ItemDetailsSnapshot detailsSnapshot = resolver.resolveDetails(itemSnapshot);
    return ItemDto.builder()
        .id(itemSnapshot.getId())
        .name(detailsSnapshot.getName())
        .stock(itemSnapshot.getStock())
        .vat((item.getVat().asDouble()))
        .valueGross(item.getValueGross().asDecimal())
        .imageId(itemSnapshot.getImageId())
        .build();
  }
}