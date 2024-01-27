package com.example.opencommerce.adapters.database.catalog.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
