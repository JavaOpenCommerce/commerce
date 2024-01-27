package com.example.opencommerce.adapters.database.catalog.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
