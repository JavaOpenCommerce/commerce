package com.example.javaopencommerce.order;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.ItemId;
import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import com.example.javaopencommerce.order.CardItem.CardItemSnapshot;
import com.example.javaopencommerce.order.Item.ItemSnapshot;
import com.example.javaopencommerce.order.query.CardDto;
import com.example.javaopencommerce.order.query.CardItemDto;
import com.example.javaopencommerce.order.query.ItemDto;

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
