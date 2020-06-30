package com.example.database.entity;

import com.example.utils.LocaleConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Locale;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = "producer")
public class ProducerDetails extends BaseEntity {

    private String name;
    private String description;

    @Convert(converter = LocaleConverter.class)
    private Locale lang;

    @ManyToOne
    private Producer producer;
}
