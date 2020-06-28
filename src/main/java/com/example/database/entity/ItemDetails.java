package com.example.database.entity;


import com.example.utils.LocaleConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Entity
@Indexed
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"item", "images"})
public class ItemDetails extends BaseEntity {

    @FullTextField(analyzer = "item")
    @KeywordField(name = "name_sort", sortable = Sortable.YES, normalizer = "sort")
    private String name;

    @Convert(converter = LocaleConverter.class)
    private Locale lang;

    @FullTextField(analyzer = "item")
    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    @JoinColumn
    private Item item;

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Image> images = new HashSet<>();

}
