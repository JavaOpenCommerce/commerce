package com.example.javaopencommerce.item;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.image.ImageDto;
import com.example.javaopencommerce.item.dtos.ItemDto;
import io.smallrye.mutiny.Uni;
import java.util.List;

class ItemQueryRepositoryImpl implements ItemQueryRepository {

  private final PsqlItemRepository psqlItemRepository;
  private final ItemDetailsLangResolver resolver;

  ItemQueryRepositoryImpl(PsqlItemRepository psqlItemRepository,
      ItemDetailsLangResolver resolver) {
    this.psqlItemRepository = psqlItemRepository;
    this.resolver = resolver;
  }

  @Override
  public Uni<ItemDto> getItemById(Long id) {
    Uni<List<ItemDetailsEntity>> itemDetails = psqlItemRepository.getItemDetailsListByItemId(id);
    Uni<ItemEntity> itemEntity = psqlItemRepository.getItemById(id);

    return Uni.combine().all().unis(itemEntity, itemDetails)
        .combinedWith((item, details) -> ItemDetailsMatcher
            .convertToItemModelList(singletonList(item), details))
        .map(items ->
            items.stream()
                .map(this::toDto)
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

  private ItemDto toDto(Item item) {
    ItemSnapshot itemSnapshot = item.getSnapshot();
    ItemDetailsSnapshot detailsSnapshot = resolver.resolveDetails(itemSnapshot);
    return ItemDto.builder()
        .id(itemSnapshot.getId())
        .name(detailsSnapshot.getName())
        .stock(itemSnapshot.getStock())
        .vat((item.getVat().asDouble()))
        .valueGross(item.getValueGross().asDecimal())
        .image(ImageDto.fromSnapshot(itemSnapshot.getImage()))
        .build();
  }
}