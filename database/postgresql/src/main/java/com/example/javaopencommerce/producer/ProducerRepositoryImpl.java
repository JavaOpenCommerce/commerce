package com.example.javaopencommerce.producer;

import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProducerRepositoryImpl implements ProducerRepository {

  private final PgPool client;
  private final ProducerMapper producerMapper;

  public ProducerRepositoryImpl(PgPool client) {
    this.client = client;
    this.producerMapper = new ProducerMapper();
  }


  @Override
  public ProducerEntity getProducerByItemId(Long id) {
    return this.client.preparedQuery(
            "SELECT * FROM Producer p " + "INNER JOIN producer_details pd ON pd.producer_id = p.id "
                + "INNER JOIN Item i ON i.producer_id = p.id " + "WHERE i.id = $1")
        .execute(Tuple.of(id)).map(this.producerMapper::buildProducer).await().indefinitely();
  }

  @Override
  public List<ProducerEntity> getAll() {
    return this.client.preparedQuery(
            "SELECT * FROM Producer p " + "INNER JOIN producer_details pd ON pd.producer_id = p.id ")
        .execute().map(this.producerMapper::rowToProducerList).await().indefinitely();
  }

  @Override
  public List<ProducerEntity> getProducersListByIdList(List<Long> ids) {
    return this.client.preparedQuery(
            "SELECT * FROM Producer p " + "INNER JOIN producer_details pd ON pd.producer_id = p.id "
                + "INNER JOIN Item i ON i.producer_id = p.id " + "WHERE i.id = ANY ($1)")
        .execute(Tuple.of(ids.toArray(new Long[ids.size()])))
        .map(this.producerMapper::rowToProducerList).await().indefinitely();
  }


}
