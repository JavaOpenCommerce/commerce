package com.example.javaopencommerce.order;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.catalog.CardItemEntity;
import com.example.javaopencommerce.catalog.ItemQueryRepository;
import com.example.javaopencommerce.catalog.dtos.ItemDto;
import com.example.javaopencommerce.order.CardItem.CardItemSnapshot;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@Log4j2
class CardRepositoryImpl implements CardRepository {

  private final RedisCardRepository redisCardRepository;
  private final ItemQueryRepository itemRepository;
  private final ItemMapper itemMapper;

  public CardRepositoryImpl(RedisCardRepository redisCardRepository,
      ItemQueryRepository itemRepository, ItemMapper itemMapper) {
    this.redisCardRepository = redisCardRepository;
    this.itemRepository = itemRepository;
    this.itemMapper = itemMapper;
  }


  @Override
  public List<CardItem> getCardList(String id) {
    return restoreProducts(redisCardRepository.getCardList(id));
  }

  @Override
  public List<CardItem> saveCard(String id, List<CardItem> products) {
    List<CardItemEntity> productEntities = products.stream().map(CardItem::getSnapshot)
        .map(this::fromSnapshot).toList();

    return restoreProducts(redisCardRepository.saveCard(id, productEntities));
  }

  @Override
  public void flushCard(String id) {
    redisCardRepository.flushCard(id);
  }

  private List<CardItem> restoreProducts(List<CardItemEntity> cardItemEntities) {
    List<Long> itemIds = cardItemEntities.stream().map(CardItemEntity::itemId).toList();
    List<ItemDto> items = itemRepository.getItemsByIdList(itemIds);

    List<CardItem> cardItems = new ArrayList<>();
    for (CardItemEntity cardItemEntity : cardItemEntities) {
      ItemDto matchedItem = items.stream().filter(item -> hasMatchingIds(item, cardItemEntity))
          .findFirst().orElseThrow(); // TODO proper handling
      Item matchedItemModel = itemMapper.fromCatalog(matchedItem);
      cardItems.add(CardItem.withAmount(matchedItemModel, Amount.of(cardItemEntity.amount())));
    }
    return cardItems;
  }

  private boolean hasMatchingIds(ItemDto item, CardItemEntity itemEntity) {
    return item.getId().equals(itemEntity.itemId());
  }

  private CardItemEntity fromSnapshot(CardItemSnapshot snapshot) {
    return new CardItemEntity(snapshot.id().id(), snapshot.amount().getValue());
  }
}
