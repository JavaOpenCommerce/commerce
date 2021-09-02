package com.example.database.repositories.impl;

import com.example.database.entity.Producer;
import com.example.database.repositories.impl.mappers.ProducerMapper;
import com.example.database.repositories.interfaces.ProducerRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ProducerRepositoryImpl implements ProducerRepository {

    private final PgPool client;
    private final ProducerMapper producerMapper;

    public ProducerRepositoryImpl(PgPool client, ProducerMapper producerMapper) {
        this.client = client;
        this.producerMapper = producerMapper;
    }


    @Override
    public Uni<Producer> getProducerByItemId(Long id) {
        return this.client.preparedQuery("SELECT * FROM Producer p " +
                        "INNER JOIN Image img ON p.image_id = img.id " +
                        "INNER JOIN producer_details pd ON pd.producer_id = p.id " +
                        "INNER JOIN Item i ON i.producer_id = p.id " +
                        "WHERE i.id = $1")
                .execute(Tuple.of(id))
                .map(this.producerMapper::buildProducer);
    }

    @Override
    public Uni<List<Producer>> getAll() {
        return this.client.preparedQuery("SELECT * FROM Producer p " +
                        "INNER JOIN Image img ON p.image_id = img.id " +
                        "INNER JOIN producer_details pd ON pd.producer_id = p.id ")
                .execute()
                .map(this.producerMapper::rowToProducerList);
    }

    @Override
    public Uni<List<Producer>> getProducersListByIdList(List<Long> ids) {
        return this.client.preparedQuery("SELECT * FROM Producer p " +
                        "INNER JOIN Image img ON p.image_id = img.id " +
                        "INNER JOIN producer_details pd ON pd.producer_id = p.id " +
                        "INNER JOIN Item i ON i.producer_id = p.id " +
                        "WHERE i.id = ANY ($1)")
                .execute(Tuple.of(ids.toArray(new Long[ids.size()])))
                .map(this.producerMapper::rowToProducerList);
    }


}
