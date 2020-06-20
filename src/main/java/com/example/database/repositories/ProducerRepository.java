package com.example.database.repositories;

import com.example.database.entity.Producer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProducerRepository implements PanacheRepository<Producer> {
}
