package com.example.database.entity;

import com.example.utils.LocaleConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = "items")
public class Category extends BaseEntity {


    private String categoryName;
    private String description;

    @Convert(converter = LocaleConverter.class)
    private Locale lang;

    @Builder.Default
    @ManyToMany(mappedBy = "category")
    Set<Item> items = new HashSet<>();

}
