package com.example.javaopencommerce.warehouse;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "warehouse_item_details")
class WarehouseItemDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shortDescription;
    private String location;
    private double unitWeight; // what bout unit of measure?
    private String unitDimension; // what bout unit of measure?
}
