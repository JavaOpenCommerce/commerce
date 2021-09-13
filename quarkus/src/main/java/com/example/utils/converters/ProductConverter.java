package com.example.utils.converters;

import com.example.javaopencommerce.order.CardProduct;
import com.example.javaopencommerce.order.ProductDto;
import com.example.javaopencommerce.order.ProductModel;


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
