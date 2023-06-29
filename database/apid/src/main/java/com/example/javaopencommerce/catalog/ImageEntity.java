package com.example.javaopencommerce.catalog;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Entity(name = "Image")
@Table(name = "image")
class ImageEntity {

  @Id
  @GeneratedValue
  private Long id;

  private String alt;
  private String url;
}
