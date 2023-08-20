package com.example.opencommerce.adapters.database.catalog.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
