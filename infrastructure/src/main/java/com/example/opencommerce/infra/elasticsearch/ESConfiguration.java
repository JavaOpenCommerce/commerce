package com.example.opencommerce.infra.elasticsearch;

import com.example.opencommerce.adapters.database.catalog.elastic.ElasticSearchClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

class ESConfiguration {

    @Produces
    @ApplicationScoped
    @ElasticSearchClient
    WebClient elasticSearchMutinyWebClient(Vertx vertx, ElasticAddress address) {
        return WebClient.create(vertx, new WebClientOptions()
                .setDefaultPort(address.getPort())
                .setDefaultHost(address.getHost()));
    }

    @Produces
    @ApplicationScoped
    @ElasticSearchClient
    io.vertx.ext.web.client.WebClient elasticSearchWebClient(io.vertx.core.Vertx vertx, ElasticAddress address) {
        return io.vertx.ext.web.client.WebClient.create(vertx,
                new WebClientOptions().setDefaultPort(address.getPort())
                        .setDefaultHost(address.getHost()));
    }
}
