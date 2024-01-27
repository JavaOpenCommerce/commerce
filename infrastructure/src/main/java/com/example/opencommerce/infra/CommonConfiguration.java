package com.example.opencommerce.infra;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;

class CommonConfiguration {

    @Produces
    @ApplicationScoped
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper
                .registerModule(new JavaTimeModule())
                .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES))
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}
