package com.example.javaopencommerce.order;

import com.example.javaopencommerce.producer.ProducerEntity;
import com.example.javaopencommerce.producer.ProducerRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ProducerRepositoryImpl implements ProducerRepository {

    private final PgPool client;
    private final ProducerMapper producerMapper;

    public ProducerRepositoryImpl(PgPool client) {
        this.client = client;
        this.producerMapper = new ProducerMapper();
    }


    @Override
    public Uni<ProducerEntity> getProducerByItemId(Long id) {
        return this.client.preparedQuery("SELECT * FROM Producer p " +
                        "INNER JOIN Image img ON p.image_id = img.id " +
                        "INNER JOIN producer_details pd ON pd.producer_id = p.id " +
                        "INNER JOIN Item i ON i.producer_id = p.id " +
                        "WHERE i.id = $1")
                .execute(Tuple.of(id))
                .map(this.producerMapper::buildProducer);
    }

    @Override
    public Uni<List<ProducerEntity>> getAll() {
        return this.client.preparedQuery("SELECT * FROM Producer p " +
                        "INNER JOIN Image img ON p.image_id = img.id " +
                        "INNER JOIN producer_details pd ON pd.producer_id = p.id ")
                .execute()
                .map(this.producerMapper::rowToProducerList);
    }

    @Override
    public Uni<List<ProducerEntity>> getProducersListByIdList(List<Long> ids) {
        return this.client.preparedQuery("SELECT * FROM Producer p " +
                        "INNER JOIN Image img ON p.image_id = img.id " +
                        "INNER JOIN producer_details pd ON pd.producer_id = p.id " +
                        "INNER JOIN Item i ON i.producer_id = p.id " +
                        "WHERE i.id = ANY ($1)")
                .execute(Tuple.of(ids.toArray(new Long[ids.size()])))
                .map(this.producerMapper::rowToProducerList);
    }


}
