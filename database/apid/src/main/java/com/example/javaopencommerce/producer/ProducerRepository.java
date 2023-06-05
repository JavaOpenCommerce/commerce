package com.example.javaopencommerce.producer;

import java.util.List;

public interface ProducerRepository {

  ProducerEntity getProducerByItemId(Long id);

  List<ProducerEntity> getAll();

  List<ProducerEntity> getProducersListByIdList(List<Long> ids);
}
