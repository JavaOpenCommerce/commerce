package com.example.database.entity;

import com.example.utils.LocaleConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Locale;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = "category")
public class CategoryDetails extends BaseEntity {

    private String name;
    private String description;

    @Convert(converter = LocaleConverter.class)
    private Locale lang;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
