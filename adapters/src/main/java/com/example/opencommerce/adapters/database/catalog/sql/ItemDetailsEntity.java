package com.example.opencommerce.adapters.database.catalog.sql;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ItemDetails")
@Table(name = "item_details")
class ItemDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @OneToMany
    @JoinTable(name = "item_details_image", joinColumns = {
            @JoinColumn(name = "item_details_id")}, inverseJoinColumns = {@JoinColumn(name = "image_id")})
    private List<ImageEntity> additionalImages;
}
