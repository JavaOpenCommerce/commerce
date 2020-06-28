package com.example.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@Indexed
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Item extends BaseEntity {

    private BigDecimal valueGross;

    private double vat;
    private int stock;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @IndexedEmbedded
    @ManyToMany(cascade = {REFRESH, DETACH, MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "item_producer",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "producer_id"))
    private Set<Producer> producer;

    @Builder.Default
    @IndexedEmbedded
    @ManyToMany(cascade = {REFRESH, DETACH, MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "item_category",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> category = new HashSet<>();

    @Builder.Default
    @IndexedEmbedded
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "item", cascade = CascadeType.ALL)
    private Set<ItemDetails> details = new HashSet<>();

}
