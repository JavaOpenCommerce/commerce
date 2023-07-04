package com.example.javaopencommerce.catalog;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private BigDecimal valueGross;
    private double vat;
    private int stock;
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
