package com.example.opencommerce.adapters.database.catalog.sql;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


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
