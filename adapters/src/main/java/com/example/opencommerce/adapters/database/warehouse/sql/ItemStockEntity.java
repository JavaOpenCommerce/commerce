package com.example.opencommerce.adapters.database.warehouse.sql;

import lombok.*;

import javax.persistence.*;
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
    private List<ItemReservationEntity> reservations = new ArrayList<>();
}
