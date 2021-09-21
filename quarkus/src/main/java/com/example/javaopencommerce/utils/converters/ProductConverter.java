package com.example.javaopencommerce.utils.converters;

import com.example.javaopencommerce.item.ItemConverter;
import com.example.javaopencommerce.order.CardProductEntity;
import com.example.javaopencommerce.order.Product;
import com.example.javaopencommerce.order.ProductDto;


public interface ProductConverter {

    static ProductDto convertToDto(Product product, String lang, String defaultLang) {
        return ProductDto.builder()
                .item(ItemConverter.convertToDto(product.getItemModel(), lang, defaultLang))
                .amount(product.getAmount().asInteger())
                .valueGross(product.getValueGross().asDecimal())
                .valueNett(product.getValueNett().asDecimal())
                .build();
    }

    static Product convertDtoToModel(ProductDto product) {
        return Product.getProduct(ItemConverter.convertDtoToModel(product.getItem()), product.getAmount());
    }

    static CardProductEntity convertModelToCardProduct(Product product) {
        return CardProductEntity.builder()
                .itemId(product.getItemModel().getId())
                .amount(product.getAmount().asInteger())
                .build();
    }
}
