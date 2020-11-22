package com.example.database.entity;

import lombok.*;

import java.util.Locale;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProducerDetails {

    private Long id;
    private String name;
    private String description;
    private Locale lang;
}
