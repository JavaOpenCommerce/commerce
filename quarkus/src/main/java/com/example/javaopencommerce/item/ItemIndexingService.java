package com.example.javaopencommerce.item;

import com.example.javaopencommerce.category.CategoryQueryRepository;
import com.example.javaopencommerce.elasticsearch.ElasticAddress;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import lombok.extern.log4j.Log4j2;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Log4j2
@ApplicationScoped
class ItemIndexingService {

    private final WebClient client;
    private final ItemRepository itemRepository;
    private final ObjectMapper mapper;
    private final CategoryQueryRepository categoryQueryRepository;


    ItemIndexingService(Vertx vertx,
                        ElasticAddress address,
                        ItemRepository itemRepository,
                        CategoryQueryRepository categoryQueryRepository) {
        this.itemRepository = itemRepository;
        this.categoryQueryRepository = categoryQueryRepository;
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
                log.info("'Item' index successfully cleaned: %s", ar.result().bodyAsString());
            } else {
                log.info("Problems during 'item' index cleanup: %s", ar.result().bodyAsJsonObject());
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
                .putHeader("Content-Type", APPLICATION_JSON)
                .sendJson(settingsPayload, ar -> {
                            if (ar.succeeded()) {
                                log.info("Settings for index 'item' successfully updated, %s",
                                        ar.result().bodyAsString());
                            } else {
                                log.warn("Troubles during update of 'item' index settings, %s",
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
            log.warn("Failed to parse json with index settings: %s", e.getMessage());
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
            log.error("Failed to load elastic search settings file: %s", e.getMessage());
            return empty();
        }
    }

    private void fetchItems() {
        getSearchItems().forEach(this::sendItem);
    }

    private void sendItem(SearchItem item) {
        client.put("/items/_doc/" + item.getId())
                .putHeader("Content-Length", String.valueOf(Json.encode(item).length()))
                .putHeader("Content-Type", APPLICATION_JSON)
                .sendJson(item, ar -> {
                    if (ar.succeeded()) {
                        log.info("Item with id %s, successfully indexed, %s", item.getId(),
                                ar.result().bodyAsString());
                    } else {
                        log.info("Troubles during indexing item with id: %s, %s", item.getId(),
                                ar.result().bodyAsJsonObject());
                    }
                });
    }

    private List<SearchItem> getSearchItems() {
        return itemRepository.getAllItems().stream().map(Item::getSnapshot)
                .map(SearchItemConverter::convertToSearchItem).map(this::enrichSearchItemWithCategoryIds)
                .toList();
    }

    private SearchItem enrichSearchItemWithCategoryIds(SearchItem item) {
        List<Long> itemCategoryIds = categoryQueryRepository.getCategoryIdsByItemId(item.getId());
        item.setCategoryIds(itemCategoryIds);
        return item;
    }
}
