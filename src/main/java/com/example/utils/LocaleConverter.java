package com.example.utils;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.AttributeConverter;
import java.util.Locale;

@ApplicationScoped
public class LocaleConverter implements AttributeConverter<Locale, String> {

    @Override
    public String convertToDatabaseColumn(Locale attribute) {
        return attribute.toString();
    }

    @Override
    public Locale convertToEntityAttribute(String dbData) {
        return Locale.forLanguageTag(dbData);
    }
}
