package com.example.opencommerce.adapters.database.catalog.sql;


import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Item")
@Table(name = "item")
class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(
            name = "item_category",
            joinColumns = @JoinColumn(name = "item_id")
    )
    @Column(name = "category_id")
    private List<UUID> categoryIds;
    private String name;
    private boolean shipping;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producer_id")
    private ProducerEntity producer;

    @OneToOne
    @JoinColumn(name = "image_id")
    private ImageEntity image;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_details_id")
    private ItemDetailsEntity details;
}
