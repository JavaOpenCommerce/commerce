package com.example.javaopencommerce.utils;


import static java.util.Locale.forLanguageTag;

import io.vertx.ext.web.LanguageHeader;
import io.vertx.ext.web.RoutingContext;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class LanguageResolver {

    @ConfigProperty(name = "com.example.default-locale")
    private String lang;

    private final RoutingContext context;

    public LanguageResolver(RoutingContext context) {this.context = context;}

    public String getLanguage() {
        List<LanguageHeader> languages = context.acceptableLanguages();

        return languages.stream()
                .filter(Objects::nonNull)
                .map(l -> l.value().split("-")[0])
                .findFirst()
                .orElse(lang);
    }

    public String getLanguage(RoutingContext rc) {
        List<LanguageHeader> languages = rc.acceptableLanguages();

        return languages.stream()
                .filter(Objects::nonNull)
                .map(l -> l.value().split("-")[0])
                .findFirst()
                .orElse(lang);
    }

    public String getDefault() {
        return lang;
    }

    public Locale getDefaultLocale() {
        return forLanguageTag(lang);
    }
}
