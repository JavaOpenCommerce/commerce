package com.example.javaopencommerce.producer;

import io.smallrye.mutiny.Uni;
import java.util.List;

public interface ProducerRepository {

    Uni<ProducerEntity> getProducerByItemId(Long id);

    Uni<List<ProducerEntity>> getAll();

    Uni<List<ProducerEntity>> getProducersListByIdList(List<Long> ids);
}
