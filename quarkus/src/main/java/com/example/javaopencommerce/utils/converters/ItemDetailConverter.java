package com.example.javaopencommerce.utils.converters;

import com.example.javaopencommerce.item.Item;
import com.example.javaopencommerce.item.ItemDetails;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.util.Objects;

public interface ItemDetailConverter {

    static ItemDetails getItemDetailsByLanguage(Item item, String lang, String defaultLang) {
        if (item.getDetails().isEmpty()) {
            return ItemDetails.builder().name(HttpResponseStatus.NOT_FOUND.toString()).build();
        }

        return item.getDetails().stream()
                .filter(d -> Objects.nonNull(d.getLang().getLanguage()))
                .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(lang))
                .findFirst()
                .orElse(item.getDetails().stream()
                        .filter(d -> d.getLang().getLanguage().equalsIgnoreCase(defaultLang))
                        .findFirst()
                        .orElse(ItemDetails.builder().name(HttpResponseStatus.NOT_FOUND.toString()).build()));
    }
}
