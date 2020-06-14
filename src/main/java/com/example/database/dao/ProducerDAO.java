package com.example.database.dao;

import com.example.database.entity.Producer;

import java.util.List;
import java.util.Optional;

public interface ProducerDAO {

    Optional<Producer> getById(Long id);

    List<Producer> getAll();

    Producer save(Producer producer);

    void delete(Producer producer);

    void deleteById(Long id);
}
