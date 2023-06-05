package com.example.javaopencommerce.producer;

import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProducerDetailsEntity {

  private Long id;
  private String name;
  private String description;
  private Locale lang;
}
