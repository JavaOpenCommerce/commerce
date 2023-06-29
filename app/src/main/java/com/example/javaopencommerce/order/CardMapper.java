package com.example.javaopencommerce.order;

import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import com.example.javaopencommerce.order.CardItem.CardItemSnapshot;
import com.example.javaopencommerce.order.Item.ItemSnapshot;
import com.example.javaopencommerce.order.dtos.CardDto;
import com.example.javaopencommerce.order.dtos.CardItemDto;
import com.example.javaopencommerce.order.dtos.ItemDto;
import java.util.List;

class CardMapper {

  private CardMapper() {
  }

  static CardSnapshot toSnapshot(CardDto cardDto) {
    List<CardItemSnapshot> cardItems = cardDto.getProducts().stream().map(CardMapper::toSnapshot)
        .toList();

    return new CardSnapshot(cardItems, Value.of(cardDto.getCardValueGross()),
        Value.of(cardDto.getCardValueNett()));
  }

  private static CardItemSnapshot toSnapshot(CardItemDto cardItemDto) {
    ItemDto itemDto = cardItemDto.item();
    ItemSnapshot itemSnapshot = new ItemSnapshot(ItemId.of(itemDto.getId()),
        Amount.of(itemDto.getStock()), itemDto.getName(), itemDto.getImageUri(),
        Value.of(itemDto.getValueGross()), Vat.of(itemDto.getVat()));
    return new CardItemSnapshot(itemSnapshot, Value.of(cardItemDto.valueNett()),
        Value.of(cardItemDto.valueGross()), Amount.of(cardItemDto.amount()));
  }

  static CardDto toDto(Card card) {
    CardSnapshot cardSnapshot = card.getSnapshot();
    List<CardItemSnapshot> cardItems = cardSnapshot.items();
    List<CardItemDto> itemDtos = cardItems.stream().map(CardMapper::toDto).collect(toList());

    return CardDto.builder().products(itemDtos)
        .cardValueGross(cardSnapshot.cardValueGross().getValue())
        .cardValueNett(cardSnapshot.cardValueNett().getValue()).build();
  }

  private static CardItemDto toDto(CardItemSnapshot cardItemSnapshot) {
    ItemSnapshot itemSnapshot = cardItemSnapshot.itemSnapshot();
    ItemDto itemDto = fromSnapshot(itemSnapshot);
    return new CardItemDto(itemDto, cardItemSnapshot.valueNett().asDecimal(),
        cardItemSnapshot.valueGross().asDecimal(), cardItemSnapshot.amount().asInteger());
  }

  private static ItemDto fromSnapshot(ItemSnapshot itemSnapshot) {
    return ItemDto.builder().id(itemSnapshot.id().id()).stock(itemSnapshot.stock().asInteger())
        .valueGross(itemSnapshot.valueGross().asDecimal()).vat(itemSnapshot.vat().asDouble())
        .name(itemSnapshot.name()).imageUri(itemSnapshot.imageUrl()).build();
  }

}
