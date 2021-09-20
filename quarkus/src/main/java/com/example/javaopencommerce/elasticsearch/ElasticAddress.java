package com.example.javaopencommerce.elasticsearch;

import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@Getter
@ApplicationScoped
class ElasticAddress {

    @ConfigProperty(name = "com.example.elasticsearch-host")
    String host;

    @ConfigProperty(name = "com.example.elasticsearch-port")
    int port;
}
