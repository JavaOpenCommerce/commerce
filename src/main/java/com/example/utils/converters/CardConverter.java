package com.example.utils.converters;


import com.example.business.models.CardModel;
import com.example.database.entity.CardProduct;
import dtos.CardDto;
import dtos.ProductDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public interface CardConverter {

    static CardDto convertToDto(CardModel cardModel, String lang, String defaultLang)  {
        Map<Long, ProductDto> productDtos = new HashMap<>();

        cardModel.getProducts().values()
                .forEach(p -> productDtos.put(p.getItemModel().getId(), ProductConverter.convertToDto(p, lang, defaultLang)));

        return CardDto.builder()
                .cardValueGross(cardModel.getCardValueGross().asDecimal())
                .cardValueNett(cardModel.getCardValueNett().asDecimal())
                .products(productDtos)
                .build();
    }

    static List<CardProduct> convertToProductList(CardModel cardModel) {
        return cardModel.getProducts().values()
                .stream()
                .map(p -> CardProduct.builder()
                        .itemId(p.getItemModel().getId())
                        .amount(p.getAmount().asInteger())
                        .build())
                .collect(toList());
    }
}
