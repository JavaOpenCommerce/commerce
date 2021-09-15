package com.example.javaopencommerce.utils.converters;


import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.order.CardDto;
import com.example.javaopencommerce.order.Card;
import com.example.javaopencommerce.order.CardProductEntity;
import com.example.javaopencommerce.order.ProductDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CardConverter {

    static CardDto convertToDto(Card cardModel, String lang, String defaultLang)  {
        Map<Long, ProductDto> productDtos = new HashMap<>();

        cardModel.getProducts().values()
                .forEach(p -> productDtos.put(p.getItemModel().getId(), ProductConverter.convertToDto(p, lang, defaultLang)));

        return CardDto.builder()
                .cardValueGross(cardModel.getCardValueGross().asDecimal())
                .cardValueNett(cardModel.getCardValueNett().asDecimal())
                .products(productDtos)
                .build();
    }

    static List<CardProductEntity> convertToProductList(Card cardModel) {
        return cardModel.getProducts().values()
                .stream()
                .map(p -> CardProductEntity.builder()
                        .itemId(p.getItemModel().getId())
                        .amount(p.getAmount().asInteger())
                        .build())
                .collect(toList());
    }
}
