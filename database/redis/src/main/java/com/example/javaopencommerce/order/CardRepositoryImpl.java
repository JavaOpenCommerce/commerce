package com.example.javaopencommerce.order;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.catalog.ItemQueryRepository;
import com.example.javaopencommerce.catalog.dtos.ItemDto;
import lombok.extern.log4j.Log4j2;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@ApplicationScoped
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
    public Card getCard(String id) {
        return restoreCard(redisCardRepository.getCardList(id));
    }

    @Override
    public Card saveCard(String cardId, Card card) {
        List<CardItemEntity> productEntities = card.getCardItems()
                .stream()
                .map(CardItem::getSnapshot)
                .map(CardItemEntity::fromSnapshot)
                .toList();

        return restoreCard(redisCardRepository.saveCard(cardId, productEntities));
    }

    @Override
    public void flushCard(String id) {
        redisCardRepository.flushCard(id);
    }

    private Card restoreCard(List<CardItemEntity> cardItemEntities) {
        List<Long> itemIds = cardItemEntities.stream()
                .map(CardItemEntity::itemId)
                .toList();
        List<ItemDto> items = itemRepository.getItemsByIdList(itemIds);

        List<CardItem> cardItems = new ArrayList<>();
        for (CardItemEntity cardItemEntity : cardItemEntities) {
            Optional<ItemDto> matchedItem = items.stream()
                    .filter(item -> hasMatchingIds(item, cardItemEntity))
                    .findFirst();

            if (matchedItem.isEmpty()) {
                cardItems.add(CardItem.empty(ItemId.of(cardItemEntity.itemId()), ""));
                continue;
            }

            Item matchedItemModel = itemMapper.fromCatalog(matchedItem.get());
            cardItems.add(CardItem.withAmount(matchedItemModel, Amount.of(cardItemEntity.amount())));
        }
        return Card.ofProducts(cardItems);
    }

    private boolean hasMatchingIds(ItemDto item, CardItemEntity itemEntity) {
        return item.getId()
                .equals(itemEntity.itemId());
    }
}
