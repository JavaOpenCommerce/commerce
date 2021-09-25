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
    private final ItemQueryRepository itemQueryRepository;

    public CardFacade(CardService cardService, ItemQueryRepository itemQueryRepository) {
        this.cardService = cardService;
        this.itemQueryRepository = itemQueryRepository;
    }

    public Uni<CardDto> getCard(String id) {
        return cardService
            .getCard(id)
            .flatMap(card -> {
                CardSnapshot cardSnapshot = card.getSnapshot();
                List<Uni<ProductDto>> productDtos = cardSnapshot.getProducts().stream()
                    .map(product ->
                        itemQueryRepository
                            .getItemById(product.getItemId())
                            .map(item -> toDto(product, item)))
                    .collect(toList());

                return Uni.combine().all().unis(productDtos).combinedWith(products ->
                    CardDto.builder()
                        .products((List<ProductDto>) products)
                        .cardValueGross(cardSnapshot.getCardValueGross().getValue())
                        .cardValueNett(cardSnapshot.getCardValueNett().getValue())
                        .build()
                );
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

    private ProductDto toDto(ProductSnapshot productSnapshot, ItemDto item) {
        return ProductDto.builder()
            .item(item)
            .amount(productSnapshot.getAmount().asInteger())
            .valueGross(productSnapshot.getValueGross().asDecimal())
            .valueNett(productSnapshot.getValueNett().asDecimal())
            .build();
    }
}
