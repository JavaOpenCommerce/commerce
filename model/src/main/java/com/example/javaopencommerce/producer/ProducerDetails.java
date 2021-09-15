package com.example.javaopencommerce.producer;

import java.util.Locale;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class ProducerDetails {

    private Long id;
    private String name;
    private String description;
    private Locale lang;

}
