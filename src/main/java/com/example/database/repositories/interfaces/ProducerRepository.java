package com.example.database.repositories.interfaces;

import com.example.database.entity.Producer;
import io.smallrye.mutiny.Uni;

public interface ProducerRepository {

    Uni<Producer> getProducerByItemId(Long id);
}
