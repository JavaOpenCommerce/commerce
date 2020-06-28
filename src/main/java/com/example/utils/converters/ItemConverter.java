package com.example.utils.converters;

import com.example.business.Value;
import com.example.business.Vat;
import com.example.business.models.CategoryModel;
import com.example.business.models.ItemDetailModel;
import com.example.business.models.ItemModel;
import com.example.business.models.ProducerModel;
import com.example.database.entity.Item;
import com.example.rest.dtos.ItemDto;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public interface ItemConverter {


    static ItemModel convertToModel(Item item) {

        Set<CategoryModel> categoryModels = item.getCategory()
                .stream()
                .map(category -> CategoryConverter.convertToModel(category))
                .collect(Collectors.toSet());

        Set<ProducerModel> producerModels = item.getProducer().stream()
                .map(producer -> ProducerConverter.convertToModel(producer))
                .collect(Collectors.toSet());

        Set<ItemDetailModel> itemDetailModels = item.getDetails().stream()
                .map(itemDetails -> ItemDetailConverter.convertToModel(itemDetails))
                .collect(Collectors.toSet());

        return ItemModel.builder()
                .id(item.getId())
                .valueGross(Value.of(item.getValueGross()))
                .producer(producerModels)
                .category(categoryModels)
                .vat(Vat.of(item.getVat()))
                .details(itemDetailModels)
                .stock(item.getStock())
                .image(ImageConverter.convertToModel(item.getImage()))
                .build();
    }

    static ItemDto convertToDto(ItemModel item, String lang, String defaultLang) {

        ItemDetailModel details = ItemDetailConverter.getItemDetailsByLanguage(item, lang, defaultLang);

        return ItemDto.builder()
                .id(item.getId())
                .name(details.getName())
                .valueGross(item.getValueGross().asDecimal())
                .producer(ProducerConverter.convertToDto(getProducerByLanguage(item, lang, defaultLang)))
                .vat(item.getVat().asDouble())
                .image(ImageConverter.convertToDto(item.getImage()))
                .build();
    }

    static ProducerModel getProducerByLanguage(ItemModel item, String lang, String defaultLang) {
        if (item.getProducer().isEmpty()) {
            return ProducerModel.builder().name("Error").build();
        }

        return item.getProducer().stream()
                .filter(d -> Objects.nonNull(d.getLang().getLanguage()))
                .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(lang))
                .findFirst()
                .orElse(item.getProducer().stream()
                        .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(defaultLang))
                        .findFirst()
                        .orElse(ProducerModel.builder().name("Error").build()));
    }
}
