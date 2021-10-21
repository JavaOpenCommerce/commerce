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
  private final ItemRepository itemRepository;


  ItemIndexingService(Vertx vertx, ElasticAddress address,
      ItemRepository itemRepository) {
    this.itemRepository = itemRepository;

    this.client = WebClient.create(vertx, new WebClientOptions()
        .setDefaultPort(address.getPort())
        .setDefaultHost(address.getHost()));
  }

  void cleanAndFetchIndexOnStartup(@Observes StartupEvent ev) {
    cleanIndex();
    fetchItems();
  }

  private void cleanIndex() {
    client.delete("/items").send(ar -> {
      if (ar.succeeded()) {
        log.infof("Item index successfully cleaned: %s", ar.result().bodyAsString());
      } else {
        log.infof("Problems during index cleanup: %s", ar.result().bodyAsJsonObject());
      }
    });
  }

  private void fetchItems() {
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
            log.infof("Item with id %s, successfully indexed, %s", item.getId(),
                ar.result().bodyAsString());
          } else {
            log.infof("Troubles during indexing item with id: %s, %s", item.getId(),
                ar.result().bodyAsJsonObject());
          }
        });
  }

  private Uni<List<SearchItem>> getSearchItems() {
    return itemRepository.getAllItems().map(
        itemModels -> itemModels.stream().map(Item::getSnapshot)
            .map(SearchItemConverter::convertToSearchItem)
            .collect(toList()));
  }
}
