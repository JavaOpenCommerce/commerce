package com.example.javaopencommerce.rest.services;

import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.database.services.CardService;
import com.example.javaopencommerce.item.ItemDto;
import com.example.javaopencommerce.order.CardDto;
import com.example.javaopencommerce.order.CardProductEntity;
import com.example.javaopencommerce.utils.LanguageResolver;
import com.example.javaopencommerce.utils.converters.CardConverter;
import com.example.javaopencommerce.utils.converters.ItemConverter;
import io.smallrye.mutiny.Uni;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CardDtoService {

    private final CardService cardService;
    private final LanguageResolver langResolver;

    public CardDtoService(CardService cardService, LanguageResolver langResolver) {
        this.cardService = cardService;
        this.langResolver = langResolver;
    }

    public Uni<CardDto> getCard(String id) {
        return cardService
                .getCard(id).onItem()
                .transform(cardModel -> CardConverter
                        .convertToDto(cardModel, langResolver.getLanguage(), langResolver.getDefault()));
    }

    public Uni<String> addProductWithAmount(CardProductEntity product, String id) {
        return cardService.addProductWithAmount(product, id);
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

    public Uni<List<ItemDto>> getShippingMethods() {
        return cardService
                .getShippingMethods().onItem()
                .transform(items -> items
                        .stream()
                        .map(i -> ItemConverter
                            .convertToDto(i, langResolver.getLanguage(), langResolver.getDefault()))
                        .collect(toList()));
    }
}
