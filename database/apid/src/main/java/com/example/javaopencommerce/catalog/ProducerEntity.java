package com.example.javaopencommerce.catalog;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Producer")
@Table(name = "producer")
class ProducerEntity {

  @Id
  private Long id;
  private String name;
  private String description;
  private String imageUrl;
}
