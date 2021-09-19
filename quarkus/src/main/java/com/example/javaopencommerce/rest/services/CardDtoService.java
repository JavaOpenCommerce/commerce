package com.example.javaopencommerce.rest.services;

import com.example.javaopencommerce.order.CardDto;
import com.example.javaopencommerce.order.CardProductEntity;
import com.example.javaopencommerce.order.CardService;
import com.example.javaopencommerce.utils.LanguageResolverImpl;
import com.example.javaopencommerce.utils.converters.CardConverter;
import io.smallrye.mutiny.Uni;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CardDtoService {

    private final CardService cardService;
    private final LanguageResolverImpl langResolver;

    public CardDtoService(CardService cardService, LanguageResolverImpl langResolver) {
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

//    public Uni<List<ItemDto>> getShippingMethods() {
//        return cardService
//                .getShippingMethods().onItem()
//                .transform(items -> items
//                        .stream()
//                        .map(i -> ItemConverter
//                            .convertToDto(i, langResolver.getLanguage(), langResolver.getDefault()))
//                        .collect(toList()));
//    }
}
