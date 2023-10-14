package com.example.opencommerce.infra.catalog;

import com.example.opencommerce.app.catalog.query.FullItemDto;
import com.example.opencommerce.app.catalog.query.ImageDto;
import com.example.opencommerce.app.catalog.query.ItemDto;
import com.example.opencommerce.app.pricing.query.PriceDto;
import com.example.opencommerce.app.pricing.query.PriceDto.DiscountDto;
import com.example.opencommerce.infra.catalog.views.BaseItemView;
import com.example.opencommerce.infra.catalog.views.FullItemView;
import com.example.opencommerce.infra.catalog.views.ImageView;
import com.example.opencommerce.infra.catalog.views.PriceView;
import com.example.opencommerce.infra.catalog.views.PriceView.DiscountView;

import java.util.List;

import static java.util.Objects.isNull;

class ItemViewMapper {

    BaseItemView toBaseItemView(ItemDto itemDto, PriceDto priceDto) {
        ImageView image = toImageView(itemDto.getImage());
        return new BaseItemView(itemDto.getId(), itemDto.getName(), image, priceDto.getFinalPrice());
    }

    public FullItemView toFullItemView(FullItemDto itemDto, PriceDto priceDto) {
        ImageView mainImageView = toImageView(itemDto.getMainImage());
        List<ImageView> additionalImages = itemDto.getAdditionalImages()
                .stream()
                .map(this::toImageView)
                .toList();

        PriceView price = toPriceView(priceDto);

        return new FullItemView(
                itemDto.getId(),
                itemDto.getName(),
                itemDto.getDescription(),
                mainImageView,
                itemDto.getCategoryIds(),
                itemDto.getProducer(),
                additionalImages,
                price);
    }

    private PriceView toPriceView(PriceDto dto) {
        DiscountView discount = toDiscountView(dto.getDiscount());
        return new PriceView(dto.getBasePriceNett(), dto.getBasePriceGross(), dto.getVat(), discount);
    }

    private DiscountView toDiscountView(DiscountDto dto) {
        if (isNull(dto)) {
            return null;
        }
        return new DiscountView(
                dto.discountedPriceNett(),
                dto.discountedPriceGross(),
                dto.lowestPriceBeforeDiscountGross());
    }

    private ImageView toImageView(ImageDto dto) {
        if (isNull(dto)) {
            return null;
        }
        return new ImageView(dto.getAlt(), dto.getUrl());
    }
}
