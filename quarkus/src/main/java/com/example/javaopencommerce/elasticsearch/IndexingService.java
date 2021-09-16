package com.example.javaopencommerce.elasticsearch;

import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.item.ItemService;
import com.example.javaopencommerce.utils.converters.SearchItemConverter;
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
public class IndexingService {

    private final WebClient client;
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
                itemModels -> itemModels.stream()
                        .map(SearchItemConverter::convertToSearchItem)
                        .collect(toList()));
    }


}
