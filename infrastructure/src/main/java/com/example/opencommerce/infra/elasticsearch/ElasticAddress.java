package com.example.opencommerce.infra.elasticsearch;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Getter
@ApplicationScoped
class ElasticAddress {

    @ConfigProperty(name = "com.example.elasticsearch-host")
    String host;

    @ConfigProperty(name = "com.example.elasticsearch-port")
    int port;
}
