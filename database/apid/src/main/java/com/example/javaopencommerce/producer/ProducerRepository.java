package com.example.javaopencommerce.producer;

import io.smallrye.mutiny.Uni;
import java.util.List;

public interface ProducerRepository {

    Uni<Producer> getProducerByItemId(Long id);

    Uni<List<Producer>> getAll();

    Uni<List<Producer>> getProducersListByIdList(List<Long> ids);
}
