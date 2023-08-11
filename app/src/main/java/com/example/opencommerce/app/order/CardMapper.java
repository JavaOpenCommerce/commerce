package com.example.opencommerce.app.order;

import com.example.opencommerce.app.order.query.CardDto;
import com.example.opencommerce.app.order.query.CardItemDto;
import com.example.opencommerce.app.order.query.ItemDto;
import com.example.opencommerce.domain.Amount;
import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.Value;
import com.example.opencommerce.domain.Vat;
import com.example.opencommerce.domain.order.Card;
import com.example.opencommerce.domain.order.CardItem.CardItemSnapshot;
import com.example.opencommerce.domain.order.CardSnapshot;
import com.example.opencommerce.domain.order.Item.ItemSnapshot;

import java.util.List;

import static java.util.stream.Collectors.toList;

class CardMapper {

    private CardMapper() {
    }

    static CardSnapshot toSnapshot(CardDto cardDto) {
        List<CardItemSnapshot> cardItems = cardDto.getProducts()
                .stream()
                .map(CardMapper::toSnapshot)
                .toList();

        return new CardSnapshot(cardItems,
                Value.of(cardDto.getCardValueNett()), Value.of(cardDto.getCardValueGross()));
    }

    private static CardItemSnapshot toSnapshot(CardItemDto cardItemDto) {
        ItemDto itemDto = cardItemDto.item();
        ItemSnapshot itemSnapshot = new ItemSnapshot(ItemId.of(itemDto.id()),
                Amount.of(itemDto.stock()), itemDto.name(), itemDto.imageUri(),
                Value.of(itemDto.valueGross()), Vat.of(itemDto.vat()));
        return new CardItemSnapshot(itemSnapshot, Value.of(cardItemDto.valueNett()),
                Value.of(cardItemDto.valueGross()), Amount.of(cardItemDto.amount()), null);
    }

    static CardDto toDto(Card card) {
        CardSnapshot cardSnapshot = card.getSnapshot();
        List<CardItemSnapshot> cardItems = cardSnapshot.items();
        List<CardItemDto> itemDtos = cardItems.stream()
                .map(CardMapper::toDto)
                .collect(toList());

        return CardDto.builder()
                .products(itemDtos)
                .cardValueGross(cardSnapshot.cardValueGross()
                        .asDecimal())
                .cardValueNett(cardSnapshot.cardValueNett()
                        .asDecimal())
                .build();
    }

    private static CardItemDto toDto(CardItemSnapshot cardItemSnapshot) {
        ItemSnapshot itemSnapshot = cardItemSnapshot.itemSnapshot();
        ItemDto itemDto = fromSnapshot(itemSnapshot);
        return new CardItemDto(itemDto, cardItemSnapshot.valueNett()
                .asDecimal(),
                cardItemSnapshot.valueGross()
                        .asDecimal(), cardItemSnapshot.amount()
                .asInteger(),
                cardItemSnapshot.status());
    }

    private static ItemDto fromSnapshot(ItemSnapshot itemSnapshot) {
        return ItemDto.builder()
                .id(itemSnapshot.id()
                        .asLong())
                .stock(itemSnapshot.stock()
                        .asInteger())
                .valueGross(itemSnapshot.valueGross()
                        .asDecimal())
                .vat(itemSnapshot.vat()
                        .asDouble())
                .name(itemSnapshot.name())
                .imageUri(itemSnapshot.imageUrl())
                .build();
    }

}
