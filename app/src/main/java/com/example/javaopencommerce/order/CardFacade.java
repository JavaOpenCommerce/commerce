package com.example.javaopencommerce.order;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.catalog.ItemQueryRepository;
import com.example.javaopencommerce.order.dtos.CardDto;

public class CardFacade {

    private final ItemQueryRepository itemRepository;
    private final CardRepository cardRepository;
    private final ItemMapper itemMapper;

    public CardFacade(ItemQueryRepository itemRepository, CardRepository cardRepository,
                      ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.cardRepository = cardRepository;
        this.itemMapper = itemMapper;
    }

    public CardDto getCard(String cardId) {
        Card card = cardRepository.getCard(cardId);
        return CardMapper.toDto(card);
    }

    public CardDto addItemWithAmount(Long itemId, int amount, String cardId) {
        Card card = cardRepository.getCard(cardId);
        Item item = itemMapper.fromCatalog(this.itemRepository.getItemById(itemId));
        card.addItem(item, Amount.of(amount));
        this.cardRepository.saveCard(cardId, card);
        return CardMapper.toDto(card);
    }

    public CardDto changeItemAmount(Long itemId, int amount, String cardId) {
        Card card = cardRepository.getCard(cardId);
        Item item = itemMapper.fromCatalog(this.itemRepository.getItemById(itemId));
        card.changeItemAmount(item, Amount.of(amount));
        this.cardRepository.saveCard(cardId, card);
        return CardMapper.toDto(card);
    }

    public CardDto removeProduct(Long itemId, String cardId) {
        Card card = this.cardRepository.getCard(cardId);
        card.removeItem(ItemId.of(itemId));
        this.cardRepository.saveCard(cardId, card);
        return CardMapper.toDto(card);
    }

    public void flushCard(String id) {
        this.cardRepository.flushCard(id);
    }
}
