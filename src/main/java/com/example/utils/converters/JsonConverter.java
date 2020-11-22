package com.example.utils.converters;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.lang.reflect.Type;
import java.util.Objects;

public abstract class JsonConverter {

    private JsonConverter() {
    }

    private static final Jsonb jsonb = JsonbBuilder.create();

    public static <T> T convertToObject(String json, Type type) {
        return jsonb.fromJson(json, type);
    }

    public static String convertToJson(Object object) {
        if (Objects.isNull(object)) {
            return "{}";
        }
        return jsonb.toJson(object);
    }
}
