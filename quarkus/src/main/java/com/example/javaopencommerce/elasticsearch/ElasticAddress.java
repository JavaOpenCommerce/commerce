package com.example.javaopencommerce.elasticsearch;

import javax.enterprise.context.ApplicationScoped;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Getter
@ApplicationScoped
public class ElasticAddress {

  @ConfigProperty(name = "com.example.elasticsearch-host")
  String host;

  @ConfigProperty(name = "com.example.elasticsearch-port")
  int port;
}
