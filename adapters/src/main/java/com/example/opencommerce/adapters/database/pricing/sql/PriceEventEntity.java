package com.example.opencommerce.adapters.database.pricing.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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
