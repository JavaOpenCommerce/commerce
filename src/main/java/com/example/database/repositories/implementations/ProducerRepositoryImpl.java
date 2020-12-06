package com.example.database.repositories.implementations;

import com.example.database.entity.Image;
import com.example.database.entity.Producer;
import com.example.database.entity.ProducerDetails;
import com.example.database.repositories.interfaces.ProducerRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@ApplicationScoped
public class ProducerRepositoryImpl implements ProducerRepository {

    private final PgPool client;

    public ProducerRepositoryImpl(PgPool client) {this.client = client;}


    @Override
    public Uni<Producer> getProducerByItemId(Long id) {
        return this.client.preparedQuery("SELECT * FROM Producer p " +
                                        "INNER JOIN Image img ON p.image_id = img.id " +
                                        "INNER JOIN ProducerDetails pd ON pd.producer_id = p.id " +
                                        "INNER JOIN Item i ON i.producer_id = p.id " +
                                        "WHERE i.id = $1")
                .execute(Tuple.of(id))
                .map(this::buildProducer);
    }

    @Override
    public Uni<List<Producer>> getAll() {
        return this.client.preparedQuery("SELECT * FROM Producer p " +
                                        "INNER JOIN Image img ON p.image_id = img.id " +
                                        "INNER JOIN ProducerDetails pd ON pd.producer_id = p.id ")
                .execute()
                .map(this::rowToProducerList);
    }

    @Override
    public Uni<List<Producer>> getProducersListByIdList(List<Long> ids) {
        return this.client.preparedQuery("SELECT * FROM Producer p " +
                                        "INNER JOIN Image img ON p.image_id = img.id " +
                                        "INNER JOIN ProducerDetails pd ON pd.producer_id = p.id " +
                                        "INNER JOIN Item i ON i.producer_id = p.id " +
                                        "WHERE i.id = ANY ($1)")
                .execute(Tuple.of(ids.toArray(new Long[ids.size()])))
                .map(this::rowToProducerList);
    }

    private List<Producer> rowToProducerList(RowSet<Row> rs) {
        if (rs == null) {
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

    private Producer buildProducer(RowSet<Row> rs) {
        if (rs == null || rs.next() == null) {
            return Producer.builder().build();
        }

        List<ProducerDetails> details = stream(rs.spliterator(), false)
                .map(this::rowToProducerDetails)
                .collect(toList());

        return buildProducer(rs.iterator().next(), details);
    }

    private Producer buildProducer(Row row, List<ProducerDetails> details) {
        if (row == null) {
            return Producer.builder().build();
        }

        return Producer.builder()
                .id(row.getLong("producer_id"))
                .details(details)
                .image(Image.builder()
                        .id(row.getLong("image_id"))
                        .alt(row.getString("alt"))
                        .url(row.getString("url"))
                        .build())
                .build();
    }

    private ProducerDetails rowToProducerDetails(Row row) {
        if (row == null) {
            return ProducerDetails.builder().build();
        }

        return ProducerDetails.builder()
                .id(row.getLong(5))
                .name(row.getString("name"))
                .description(row.getString("description"))
                .lang(Locale.forLanguageTag(row.getString("lang")))
                .build();
    }
}
