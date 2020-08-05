package com.example.elasticsearch;

import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@Getter
@ApplicationScoped
public class ElasticAddress {

    @ConfigProperty(name = "com.example.elasticsearch-host")
    private String host;

    @ConfigProperty(name = "com.example.elasticsearch-port")
    private int port;
}
