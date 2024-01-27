package com.example.opencommerce.adapters.database.warehouse.sql;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
