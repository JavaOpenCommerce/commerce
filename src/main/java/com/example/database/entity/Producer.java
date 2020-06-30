package com.example.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = "details")
public class Producer extends BaseEntity {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "producer", cascade = CascadeType.ALL)
    private Set<ProducerDetails> details;

    @OneToOne
    @JoinColumn(name= "image_id")
    private Image image;

    @Builder.Default
    @OneToMany(mappedBy = "producer")
    private Set<Item> items = new HashSet<>();

}
