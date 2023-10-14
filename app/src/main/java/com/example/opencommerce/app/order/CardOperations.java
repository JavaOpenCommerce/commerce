package com.example.opencommerce.app.order;

import com.example.opencommerce.app.catalog.query.ItemQueryRepository;
import com.example.opencommerce.app.order.query.CardDto;
import com.example.opencommerce.app.pricing.query.PriceQueryRepository;
import com.example.opencommerce.app.warehouse.query.WarehouseQueryRepository;
import com.example.opencommerce.domain.Amount;
import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.order.Card;
import com.example.opencommerce.domain.order.CardRepository;
import com.example.opencommerce.domain.order.Item;

public class CardOperations {

    private final CardFactory cardFactory;
    private final ItemQueryRepository itemRepository;
    private final WarehouseQueryRepository warehouseRepository;
    private final CardRepository cardRepository;
    private final PriceQueryRepository priceRepository;
    private final ItemMapper itemMapper;

    CardOperations(CardFactory cardFactory,
                   ItemQueryRepository itemRepository,
                   WarehouseQueryRepository warehouseRepository,
                   CardRepository cardRepository,
                   PriceQueryRepository priceRepository,
                   ItemMapper itemMapper) {
        this.cardFactory = cardFactory;
        this.itemRepository = itemRepository;
        this.warehouseRepository = warehouseRepository;
        this.cardRepository = cardRepository;
        this.priceRepository = priceRepository;
        this.itemMapper = itemMapper;
    }

    public CardDto getCard(String cardId) {
        Card card = cardFactory.restoreCard(cardRepository.getCard(cardId));
        return CardMapper.toDto(card);
    }

    public CardDto addItemWithAmount(ItemId itemId, Amount amount, String cardId) {
        Card card = cardFactory.restoreCard(cardRepository.getCard(cardId));
        Item item = itemMapper.toModel(
                this.itemRepository.getItemById(itemId),
                warehouseRepository.getAvailableStockById(itemId),
                priceRepository.getPriceByItemId(itemId));
        card.addItem(item, amount);
        this.cardRepository.saveCard(cardId, card);
        return CardMapper.toDto(card);
    }

    public CardDto changeItemAmount(ItemId itemId, Amount amount, String cardId) {
        Card card = cardFactory.restoreCard(cardRepository.getCard(cardId));
        Item item = itemMapper.toModel(
                this.itemRepository.getItemById(itemId),
                warehouseRepository.getAvailableStockById(itemId),
                priceRepository.getPriceByItemId(itemId));
        card.changeItemAmount(item, amount);
        this.cardRepository.saveCard(cardId, card);
        return CardMapper.toDto(card);
    }

    public CardDto removeProduct(ItemId itemId, String cardId) {
        Card card = cardFactory.restoreCard(cardRepository.getCard(cardId));
        card.removeItem(itemId);
        this.cardRepository.saveCard(cardId, card);
        return CardMapper.toDto(card);
    }

    public void flushCard(String id) {
        this.cardRepository.removeCard(id);
    }
}
