package com.example.elasticsearch;

import com.example.database.repositories.ItemRepository;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.json.Json;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.client.WebClient;
import lombok.extern.jbosslog.JBossLog;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.utils.converters.ItemConverter.convertToModel;
import static com.example.utils.converters.SearchItemConverter.convertToSearchItem;

@JBossLog
@ApplicationScoped
public class IndexingService {

    private WebClient client;
    private final ItemRepository repository;


    public IndexingService(ItemRepository repository, Vertx vertx, ElasticAddress address) {
        this.repository = repository;

        this.client = WebClient.create(vertx, new WebClientOptions()
                .setDefaultPort(address.getPort())
                .setDefaultHost(address.getHost()));
    }

    public void fetchIndexOnStartup(@Observes StartupEvent ev) {
        fetchItems();
    }

    public void fetchItems() {
        for (SearchItem item : getSearchItems()) {
            client.put("/items/_doc/")
                    .putHeader("Content-Length", "" + Json.encode(item).length())
                    .putHeader("Content-Type", "application/json")
                    .sendJson(item, ar -> {
                        if (ar.succeeded()) {
                            log.infof("Item with id %s, successfully indexed", item.getId());
                        } else {
                            log.info(ar.result().bodyAsJsonObject());
                        }
                    });
        }
    }

    private List<SearchItem> getSearchItems() {
        return repository.findAll().stream()
                .map(item -> convertToSearchItem(convertToModel(item)))
                .collect(Collectors.toList());
    }
}
