package com.example.javaopencommerce.producer;

import lombok.Builder;
import lombok.Value;

import java.util.Locale;

@Value
@Builder
public class ProducerDetails {

    Long id;
    String name;
    String description;
    Locale lang;

}
