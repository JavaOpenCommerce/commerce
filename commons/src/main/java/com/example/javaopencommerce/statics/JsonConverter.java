package com.example.javaopencommerce.statics;

import java.lang.reflect.Type;
import java.util.Objects;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public abstract class JsonConverter {

  private static final Jsonb jsonb = JsonbBuilder.create();

  private JsonConverter() {
  }

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
