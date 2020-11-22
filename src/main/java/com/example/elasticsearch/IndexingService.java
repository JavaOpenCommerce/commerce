package com.example.elasticsearch;

import com.example.database.services.ItemService;
import com.example.utils.converters.SearchItemConverter;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Uni;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import lombok.extern.jbosslog.JBossLog;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.util.List;

import static java.util.stream.Collectors.toList;

@JBossLog
@ApplicationScoped
public class IndexingService {

    private WebClient client;
    private final ItemService itemService;


    public IndexingService(ItemService itemService, Vertx vertx, ElasticAddress address) {
        this.itemService = itemService;

        this.client = WebClient.create(vertx, new WebClientOptions()
                .setDefaultPort(address.getPort())
                .setDefaultHost(address.getHost()));
    }

    public void fetchIndexOnStartup(@Observes StartupEvent ev) {
        fetchItems();
    }

    public void fetchItems() {
        getSearchItems().subscribe().with(
                items -> {
                    for (SearchItem item : items) {
                        sendItem(item);
                    }
                },
                fail -> log.info("Something gone wrong: " + fail.getMessage()));
    }

    private void sendItem(SearchItem item) {
        client.put("/items/_doc/" + item.getId())
                .putHeader("Content-Length", "" + Json.encode(item).length())
                .putHeader("Content-Type", "application/json")
                .sendJson(item, ar -> {
                    if (ar.succeeded()) {
                        log.infof("Item with id %s, successfully indexed, %s", item.getId(), ar.result().bodyAsString());
                    } else {
                        log.info(ar.result().bodyAsJsonObject());
                    }
                });
    }

    private Uni<List<SearchItem>> getSearchItems() {
        return itemService.getAllItems().onItem().apply(
                itemModels -> itemModels.stream()
                        .map(i -> SearchItemConverter.convertToSearchItem(i))
                        .collect(toList()));
    }


}
