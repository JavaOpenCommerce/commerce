package com.example.opencommerce.adapters.cache.card;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.ItemId;
import com.example.javaopencommerce.order.Card;
import com.example.javaopencommerce.order.CardItem;
import com.example.javaopencommerce.order.CardRepository;
import lombok.extern.log4j.Log4j2;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@ApplicationScoped
class CardRepositoryImpl implements CardRepository {

    private final RedisCardRepository redisCardRepository;

    public CardRepositoryImpl(RedisCardRepository redisCardRepository) {
        this.redisCardRepository = redisCardRepository;
    }


    @Override
    public Map<ItemId, Amount> getCard(String id) {
        return redisCardRepository.getCardList(id)
                .stream()
                .collect(Collectors.toMap(entity -> ItemId.of(entity.itemId()), entity -> Amount.of(entity.amount())));
    }

    @Override
    public void saveCard(String cardId, Card card) {
        List<CardItemEntity> productEntities = card.getCardItems()
                .stream()
                .map(CardItem::getSnapshot)
                .map(CardItemEntity::fromSnapshot)
                .toList();

        redisCardRepository.saveCard(cardId, productEntities);
    }

    @Override
    public void removeCard(String id) {
        redisCardRepository.removeCard(id);
    }
}
