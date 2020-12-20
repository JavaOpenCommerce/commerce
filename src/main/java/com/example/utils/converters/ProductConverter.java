package com.example.utils.converters;

import com.example.business.models.ProductModel;
import com.example.database.entity.CardProduct;
import dtos.ProductDto;


public interface ProductConverter {

    static ProductDto convertToDto(ProductModel product, String lang, String defaultLang) {
        return ProductDto.builder()
                .item(ItemConverter.convertToDto(product.getItemModel(), lang, defaultLang))
                .amount(product.getAmount().asInteger())
                .valueGross(product.getValueGross().asDecimal())
                .valueNett(product.getValueNett().asDecimal())
                .build();
    }

    static ProductModel convertDtoToModel(ProductDto product) {
        return ProductModel.getProduct(ItemConverter.convertDtoToModel(product.getItem()), product.getAmount());
    }

    static CardProduct convertModelToCardProduct(ProductModel product) {
        return CardProduct.builder()
                .itemId(product.getItemModel().getId())
                .amount(product.getAmount().asInteger())
                .build();
    }
}
