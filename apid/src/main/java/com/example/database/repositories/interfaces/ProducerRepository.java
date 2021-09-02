package com.example.database.repositories.interfaces;

import com.example.database.entity.Producer;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface ProducerRepository {

    Uni<Producer> getProducerByItemId(Long id);

    Uni<List<Producer>> getAll();

    Uni<List<Producer>> getProducersListByIdList(List<Long> ids);
}
