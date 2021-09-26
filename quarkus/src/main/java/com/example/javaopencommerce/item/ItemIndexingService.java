package com.example.javaopencommerce.item;

import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.elasticsearch.ElasticAddress;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Uni;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
@ApplicationScoped
class ItemIndexingService {

    private final WebClient client;
    private final ItemService itemService;


    ItemIndexingService(ItemService itemService, Vertx vertx, ElasticAddress address) {
        this.itemService = itemService;

        this.client = WebClient.create(vertx, new WebClientOptions()
                .setDefaultPort(address.getPort())
                .setDefaultHost(address.getHost()));
    }

    void fetchIndexOnStartup(@Observes StartupEvent ev) {
        fetchItems();
    }

    void fetchItems() {
        getSearchItems().subscribe().with(
                items -> {
                    for (SearchItem item : items) {
                        sendItem(item);
                    }
                },
            Throwable::printStackTrace);
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
        return itemService.getAllItems().map(
                itemModels -> itemModels.stream().map(item -> item.getSnapshot())
                        .map(SearchItemConverter::convertToSearchItem)
                        .collect(toList()));
    }
}
