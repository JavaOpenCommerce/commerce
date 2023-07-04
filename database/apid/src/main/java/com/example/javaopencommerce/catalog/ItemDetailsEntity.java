package com.example.javaopencommerce.catalog;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
