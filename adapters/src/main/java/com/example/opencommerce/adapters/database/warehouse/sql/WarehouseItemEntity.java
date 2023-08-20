package com.example.opencommerce.adapters.database.warehouse.sql;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "WarehouseItem")
@Table(name = "warehouse_item")
class WarehouseItemEntity {

    @Id
    private Long id;
    private Integer stock;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            orphanRemoval = true
    )
    @JoinColumn(name = "warehouse_item_details_id")
    private WarehouseItemDetailsEntity details;
}
