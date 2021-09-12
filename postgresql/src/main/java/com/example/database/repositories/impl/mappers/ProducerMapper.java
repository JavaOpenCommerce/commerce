package com.example.database.repositories.impl.mappers;

import static com.example.utils.CommonRow.isRowSetEmpty;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

import com.example.javaopencommerce.image.Image;
import com.example.javaopencommerce.producer.Producer;
import com.example.javaopencommerce.producer.ProducerDetails;
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

    public List<Producer> rowToProducerList(RowSet<Row> rs) {
        if (isRowSetEmpty(rs)) {
            return emptyList();
        }

        Map<Long, Producer> producers = new HashMap<>();
        for (Row row : rs) {
            Producer producer = buildProducer(row, new ArrayList<>());

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

    public Producer buildProducer(RowSet<Row> rs) {
        if (isRowSetEmpty(rs)) {
            return Producer.builder().build();
        }

        List<ProducerDetails> details = stream(rs.spliterator(), false)
                .map(this::rowToProducerDetails)
                .collect(toList());

        return buildProducer(rs.iterator().next(), details);
    }

    public Producer buildProducer(Row row, List<ProducerDetails> details) {
        if (row == null) {
            return Producer.builder().build();
        }

        return Producer.builder()
                .id(row.getLong(PRODUCER_ID))
                .details(details)
                .image(Image.builder()
                        .id(row.getLong(IMAGE_ID))
                        .alt(row.getString(ALT))
                        .url(row.getString(URL))
                        .build())
                .build();
    }

    public ProducerDetails rowToProducerDetails(Row row) {
        if (row == null) {
            return ProducerDetails.builder().build();
        }

        return ProducerDetails.builder()
                .id(row.getLong(5))
                .name(row.getString(NAME))
                .description(row.getString(DESCRIPTION))
                .lang(Locale.forLanguageTag(row.getString(LANG)))
                .build();
    }
}
