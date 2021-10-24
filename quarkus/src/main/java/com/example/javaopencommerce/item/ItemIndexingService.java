package com.example.javaopencommerce.item;

import static java.util.Optional.empty;
import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.elasticsearch.ElasticAddress;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Uni;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import lombok.extern.jbosslog.JBossLog;
import org.apache.http.entity.ContentType;

@JBossLog
@ApplicationScoped
class ItemIndexingService {

  private final WebClient client;
  private final ItemRepository itemRepository;
  private final ObjectMapper mapper;


  ItemIndexingService(Vertx vertx,
      ElasticAddress address,
      ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
    this.mapper = new ObjectMapper();

    this.client = WebClient.create(vertx, new WebClientOptions()
        .setDefaultPort(address.getPort())
        .setDefaultHost(address.getHost()));
  }

  void cleanAndFetchIndexOnStartup(@Observes StartupEvent ev) {
    cleanIndex();
    updateIndexSettings();
    fetchItems();
  }

  private void cleanIndex() {
    client.delete("/items").send(ar -> {
      if (ar.succeeded()) {
        log.infof("'Item' index successfully cleaned: %s", ar.result().bodyAsString());
      } else {
        log.infof("Problems during 'item' index cleanup: %s", ar.result().bodyAsJsonObject());
      }
    });
  }

  private void updateIndexSettings() {
    Optional<String> settingsJsonOptional = getSettingsJson();

    if (settingsJsonOptional.isEmpty()) {
      log.warn("Troubles during update of 'item' index settings, settings file was empty!");
      return;
    }

    TreeNode settingsPayload = getSettingsPayload(settingsJsonOptional.get());

    client.put("/items")
        .putHeader("Content-Type", ContentType.APPLICATION_JSON.toString())
        .sendJson(settingsPayload, ar -> {
              if (ar.succeeded()) {
                log.infof("Settings for index 'item' successfully updated, %s",
                    ar.result().bodyAsString());
              } else {
                log.warnf("Troubles during update of 'item' index settings, %s",
                    ar.result().bodyAsJsonObject());
              }
            }
        );
  }

  private TreeNode getSettingsPayload(String settingsJson) {
    TreeNode treeNode = null;
    try {
      JsonParser parser = mapper.getFactory().createParser(settingsJson);
      treeNode = mapper.readTree(parser);
    } catch (IOException e) {
      log.warnf("Failed to parse json with index settings: %s", e.getMessage());
    }
    return treeNode;
  }

  private Optional<String> getSettingsJson() {
    try {
      InputStream resource = Thread.currentThread().getContextClassLoader()
          .getResourceAsStream("elasticsearch_config.json");
      if (resource == null) {
        return empty();
      }
      return Optional.of(new String(resource.readAllBytes()));
    } catch (IOException e) {
      log.errorf("Failed to load elastic search settings file: %s", e.getMessage());
      return empty();
    }
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
        .putHeader("Content-Type", ContentType.APPLICATION_JSON.toString())
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
