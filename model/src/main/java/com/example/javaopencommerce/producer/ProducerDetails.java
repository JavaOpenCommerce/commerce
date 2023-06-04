package com.example.javaopencommerce.producer;

import java.util.Locale;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProducerDetails {

  Long id;
  String name;
  String description;
  Locale lang;

}
