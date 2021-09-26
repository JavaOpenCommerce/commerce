package com.example.javaopencommerce.item;

import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.item.dtos.CardDto;
import com.example.javaopencommerce.item.dtos.ItemDto;
import com.example.javaopencommerce.item.dtos.ProductDto;
import com.example.javaopencommerce.item.dtos.ProductOrder;
import io.smallrye.mutiny.Uni;
import java.util.List;

public class CardFacade {

    private final CardService cardService;
    private final ItemDtoFactory dtoFactory;

    public CardFacade(CardService cardService, ItemDtoFactory dtoFactory) {
        this.cardService = cardService;
        this.dtoFactory = dtoFactory;
    }

    public Uni<CardDto> getCard(String id) {
        return cardService
            .getCard(id)
            .map(card -> {
                CardSnapshot cardSnapshot = card.getSnapshot();
                List<ProductSnapshot> productList = cardSnapshot.getProducts();
                List<ProductDto> productDtos = productList.stream().map(this::toDto).collect(toList());

                return CardDto.builder()
                        .products(productDtos)
                        .cardValueGross(cardSnapshot.getCardValueGross().getValue())
                        .cardValueNett(cardSnapshot.getCardValueNett().getValue())
                    .build();
            });
    }

    public Uni<String> addProductWithAmount(ProductOrder order, String id) {
        return cardService.addProductWithAmount(order.getItemId(), order.getAmount(), id);
    }

    public Uni<String> increaseProductAmount(Long itemId, String id) {
        return cardService.increaseProductAmount(itemId, id);
    }

    public Uni<String> removeProduct(Long itemId, String id) {
        return cardService.removeProduct(itemId, id);
    }

    public Uni<String> decreaseProductAmount(Long itemId, String id) {
        return cardService.decreaseProductAmount(itemId, id);
    }

    public void flushCard(String id) {
        cardService.flushCard(id);
    }

    private ProductDto toDto(ProductSnapshot productSnapshot) {
        ItemSnapshot itemSnapshot = productSnapshot.getItem();
        ItemDto itemDto = dtoFactory.itemToDto(itemSnapshot);
        return ProductDto.builder()
            .item(itemDto)
            .amount(productSnapshot.getAmount().asInteger())
            .valueGross(productSnapshot.getValueGross().asDecimal())
            .valueNett(productSnapshot.getValueNett().asDecimal())
            .build();
    }
}
