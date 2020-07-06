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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ApplicationScoped
public class ProducerRepositoryImpl implements ProducerRepository {

    private final PgPool client;

    public ProducerRepositoryImpl(PgPool client) {this.client = client;}


    @Override
    public Uni<Producer> getProducerByItemId(Long id) {
        return client.preparedQuery("SELECT * FROM Producer p " +
                                        "INNER JOIN Image img ON p.image_id = img.id " +
                                        "INNER JOIN ProducerDetails pd ON pd.producer_id = p.id " +
                                        "INNER JOIN Item i ON i.producer_id = p.id " +
                                        "WHERE i.id = $1", Tuple.of(id))
                .onItem().apply(rs -> rowToProducer(rs));
    }

    private Producer rowToProducer(RowSet<Row> rs) {
        if (rs == null) {
            return Producer.builder().build();
        }

        List<ProducerDetails> details = new ArrayList<>();

        Row singleRow = null;
        for (Row row : rs) {
            singleRow = row;
            details.add(ProducerDetails.builder()
                    .id(row.getLong(5))
                    .name(row.getString("name"))
                    .description(row.getString("description"))
                    .lang(Locale.forLanguageTag(row.getString("lang")))
                    .build());
        }
        return buildProducer(details, singleRow);
    }

    private Producer buildProducer(List<ProducerDetails> details, Row row) {
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
}
