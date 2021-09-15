package com.example.javaopencommerce.order;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

import com.example.javaopencommerce.image.ImageEntity;
import com.example.javaopencommerce.producer.ProducerEntity;
import com.example.javaopencommerce.producer.ProducerDetailsEntity;
import com.example.javaopencommerce.utils.CommonRow;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProducerMapper {

    private static final String PRODUCER_ID = "producer_id";
    private static final String IMAGE_ID = "image_id";
    private static final String ALT = "alt";
    private static final String URL = "url";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String LANG = "lang";

    public List<ProducerEntity> rowToProducerList(RowSet<Row> rs) {
        if (CommonRow.isRowSetEmpty(rs)) {
            return emptyList();
        }

        Map<Long, ProducerEntity> producers = new HashMap<>();
        for (Row row : rs) {
            ProducerEntity producer = buildProducer(row, new ArrayList<>());

            ofNullable(producers.putIfAbsent(producer.getId(), producer))
                    .ifPresentOrElse(
                            prod -> prod
                                    .getDetails()
                                    .add(rowToProducerDetails(row)),
                            () -> producers
                                    .get(producer.getId())
                                    .getDetails()
                                    .add(rowToProducerDetails(row)));
        }
        return new ArrayList<>(producers.values());
    }

    public ProducerEntity buildProducer(RowSet<Row> rs) {
        if (CommonRow.isRowSetEmpty(rs)) {
            return ProducerEntity.builder().build();
        }

        List<ProducerDetailsEntity> details = stream(rs.spliterator(), false)
                .map(this::rowToProducerDetails)
                .collect(toList());

        return buildProducer(rs.iterator().next(), details);
    }

    public ProducerEntity buildProducer(Row row, List<ProducerDetailsEntity> details) {
        if (row == null) {
            return ProducerEntity.builder().build();
        }

        return ProducerEntity.builder()
                .id(row.getLong(PRODUCER_ID))
                .details(details)
                .image(ImageEntity.builder()
                        .id(row.getLong(IMAGE_ID))
                        .alt(row.getString(ALT))
                        .url(row.getString(URL))
                        .build())
                .build();
    }

    public ProducerDetailsEntity rowToProducerDetails(Row row) {
        if (row == null) {
            return ProducerDetailsEntity.builder().build();
        }

        return ProducerDetailsEntity.builder()
                .id(row.getLong(5))
                .name(row.getString(NAME))
                .description(row.getString(DESCRIPTION))
                .lang(Locale.forLanguageTag(row.getString(LANG)))
                .build();
    }
}
