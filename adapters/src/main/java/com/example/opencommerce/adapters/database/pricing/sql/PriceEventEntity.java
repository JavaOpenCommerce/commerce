package com.example.opencommerce.adapters.database.pricing.sql;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PriceEvents")
@Table(name = "item_price_event")
class PriceEventEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private Long itemId;
    private String type;
    private String data;

    private Instant validFrom;

    @CreationTimestamp
    private Instant issuedAt;
}
