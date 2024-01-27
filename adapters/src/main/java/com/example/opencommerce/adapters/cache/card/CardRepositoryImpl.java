package com.example.opencommerce.adapters.cache.card;

import com.example.opencommerce.domain.Amount;
import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.order.Card;
import com.example.opencommerce.domain.order.CardRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.log4j.Log4j2;

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
        List<CardItemEntity> productEntities = card.getSnapshot()
                .items()
                .stream()
                .map(CardItemEntity::fromSnapshot)
                .toList();

        redisCardRepository.saveCard(cardId, productEntities);
    }

    @Override
    public void removeCard(String id) {
        redisCardRepository.removeCard(id);
    }
}
