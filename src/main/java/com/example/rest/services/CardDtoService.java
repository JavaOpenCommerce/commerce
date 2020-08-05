package com.example.rest.services;

import com.example.database.entity.Product;
import com.example.database.services.CardService;
import com.example.rest.dtos.CardDto;
import com.example.rest.dtos.ItemDto;
import com.example.utils.LanguageResolver;
import com.example.utils.converters.CardConverter;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CardDtoService {

    private final CardService cardService;
    private final LanguageResolver langResolver;

    public CardDtoService(CardService cardService, LanguageResolver langResolver) {
        this.cardService = cardService;
        this.langResolver = langResolver;
    }

    public Uni<CardDto> addProductToCard(Product product, String id) {
        return cardService
                .addProductToCard(product, id)
                .onItem()
                .apply(cardModel -> CardConverter
                        .convertToDto(cardModel, langResolver.getLanguage(), langResolver.getDefault()));
    }

    public Uni<CardDto> getCard(String id) {
        return cardService
                .getCard(id)
                .onItem()
                .apply(cardModel -> CardConverter
                        .convertToDto(cardModel, langResolver.getLanguage(), langResolver.getDefault()));
    }

    public void flushCard(String id) {
        cardService.flushCard(id);
    }

    public List<ItemDto> getShippingMethods() {
        //todo
        return null;
//        cardService.getShippingMethods().stream()
//                .map(i -> ItemConverter.convertToDto(i, langResolver.getLanguage(), langResolver.getDefault()))
//                .collect(Collectors.toList());
    }
}
