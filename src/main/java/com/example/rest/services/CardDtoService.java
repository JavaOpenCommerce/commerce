package com.example.rest.services;

import com.example.database.services.CardService;
import com.example.rest.dtos.ItemDto;
import com.example.utils.LanguageResolver;
import com.example.utils.converters.ItemConverter;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CardDtoService {

    private final CardService cardService;
    private final LanguageResolver langResolver;

    public CardDtoService(CardService cardService, LanguageResolver langResolver) {this.cardService = cardService;
        this.langResolver = langResolver;
    }

    public List<ItemDto> getShippingMethods() {
        return cardService.getShippingMethods().stream()
                .map(i -> ItemConverter.convertToDto(i, langResolver.getLanguage(), langResolver.getDefault()))
                .collect(Collectors.toList());
    }
}
