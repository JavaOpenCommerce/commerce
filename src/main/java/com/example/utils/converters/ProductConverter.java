package com.example.utils.converters;

import com.example.business.models.ProductModel;
import com.example.rest.dtos.ProductDto;

public interface ProductConverter {

    static ProductDto convertToDto(ProductModel product, String lang, String defaultLang) {
        return ProductDto.builder()
                .item(ItemConverter.convertToDto(product.getItemModel(), lang, defaultLang))
                .amount(product.getAmount().asInteger())
                .valueGross(product.getValueGross().asDecimal())
                .valueNett(product.getValueNett().asDecimal())
                .build();
    }
}
