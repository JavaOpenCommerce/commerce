package com.example.opencommerce.adapters.database.warehouse.sql;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ItemStock")
@Table(name = "warehouse_item")
class ItemStockEntity {

    @Id
    private Long id;
    private Integer stock;

    @OneToMany(
            mappedBy = "itemStock",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )

    @Builder.Default
    private List<ItemReservationEntity> reservations = new ArrayList<>();
}
