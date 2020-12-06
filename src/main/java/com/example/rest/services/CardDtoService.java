package com.example.rest.services;

import com.example.database.entity.CardProduct;
import com.example.database.services.CardService;
import com.example.rest.dtos.CardDto;
import com.example.rest.dtos.ItemDto;
import com.example.utils.LanguageResolver;
import com.example.utils.converters.CardConverter;
import com.example.utils.converters.ItemConverter;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

import static java.util.stream.Collectors.toList;

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

    public Uni<String> addProductWithAmount(CardProduct product, String id) {
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
                        .map(i -> ItemConverter.convertToDto(i, langResolver.getLanguage(), langResolver.getDefault()))
                        .collect(toList()));
    }
}
