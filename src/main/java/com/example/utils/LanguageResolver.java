package com.example.utils;

import io.vertx.ext.web.LanguageHeader;
import io.vertx.ext.web.RoutingContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class LanguageResolver {

    @ConfigProperty(name = "com.example.default-locale")
    private String lang;

    private final RoutingContext context;

    public LanguageResolver(RoutingContext context) {this.context = context;}

    public String getLanguage() {
        List<LanguageHeader> languages = context.parsedHeaders().acceptLanguage();

        return languages.stream()
                .filter(Objects::nonNull)
                .map(l -> l.value().split("-")[0])
                .findFirst()
                .orElse(lang);
    }

    public String getDefault() {
        return lang;
    }
}
