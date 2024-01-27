package com.example.opencommerce.infra.catalog;

import com.example.opencommerce.adapters.database.catalog.elastic.ElasticSearchClient;
import com.example.opencommerce.adapters.database.catalog.elastic.SearchItem;
import com.example.opencommerce.app.catalog.CatalogFacade;
import com.example.opencommerce.app.catalog.CategoryEnricher;
import com.example.opencommerce.app.catalog.query.FullItemDto;
import com.example.opencommerce.app.catalog.query.ItemQueryRepository;
import com.example.opencommerce.app.pricing.query.PriceDto;
import com.example.opencommerce.app.pricing.query.PriceQueryRepository;
import com.example.opencommerce.domain.ItemId;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.json.Json;
import io.vertx.ext.web.client.WebClient;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static java.util.Optional.empty;

@Log4j2
@ApplicationScoped
class ItemIndexingService {

    private final WebClient client;
    private final ItemQueryRepository itemQueryRepository;
    private final PriceQueryRepository priceRepository;
    private final ObjectMapper mapper;
    private final CatalogFacade catalogFacade;

    private List<SearchItem> catalog = new ArrayList<>();


    ItemIndexingService(@ElasticSearchClient WebClient client,
                        ItemQueryRepository itemQueryRepository,
                        PriceQueryRepository priceRepository,
                        CatalogFacade catalogFacade) {
        this.itemQueryRepository = itemQueryRepository;
        this.catalogFacade = catalogFacade;
        this.priceRepository = priceRepository;
        this.mapper = new ObjectMapper();
        this.client = client;
    }

    void cleanAndFetchIndexOnStartup(@Observes @Priority(Integer.MAX_VALUE) StartupEvent ev) {
        catalog = getSearchItems(); // db operations on main thread.
        refreshIndex();
    }

    private void refreshIndex() {
        client.delete("/items")
                .send(ar -> {
                    if (ar.succeeded()) {
                        log.info("'Item' index successfully cleaned: {}", ar.result()
                                .bodyAsString());
                        updateIndexSettings();
                    } else {
                        log.info("Problems during 'item' index cleanup: {}", ar.result()
                                .bodyAsJsonObject());
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
                        log.info("Settings for index 'item' successfully updated, {}",
                                ar.result()
                                        .bodyAsString());
                        populateIndex();
                    } else {
                        log.warn("Troubles during update of 'item' index settings, {}",
                                ar.result()
                                        .bodyAsJsonObject());
                    }
                });
    }

    private TreeNode getSettingsPayload(String settingsJson) {
        TreeNode treeNode = null;
        try {
            JsonParser parser = mapper.getFactory()
                    .createParser(settingsJson);
            treeNode = mapper.readTree(parser);
        } catch (IOException e) {
            log.warn("Failed to parse json with index settings: {}", e.getMessage());
        }
        return treeNode;
    }

    private Optional<String> getSettingsJson() {
        try {
            InputStream resource = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("elasticsearch_config.json");
            if (resource == null) {
                return empty();
            }
            return Optional.of(new String(resource.readAllBytes()));
        } catch (IOException e) {
            log.error("Failed to load elastic search settings file: {}", e.getMessage());
            return empty();
        }
    }

    private void populateIndex() {
        catalog.forEach(this::sendItem);
    }

    private void sendItem(SearchItem item) {
        client.put("/items/_doc/" + item.id())
                .putHeader("Content-Length", String.valueOf(Json.encode(item)
                        .length()))
                .putHeader("Content-Type", APPLICATION_JSON)
                .sendJson(item, ar -> {
                    if (ar.succeeded()) { // TODO might return 200 but with error
                        log.info("Item with id {}, successfully indexed, {}", item.id(),
                                ar.result()
                                        .bodyAsString());
                    } else {
                        log.info("Troubles during indexing item with id: {}, {}", item.id(),
                                ar.result()
                                        .bodyAsJsonObject());
                    }
                });
    }


    private List<SearchItem> getSearchItems() {
        List<FullItemDto> allFullItems = itemQueryRepository.getAllFullItems();
        List<ItemId> ids = allFullItems.stream()
                .map(FullItemDto::getId)
                .map(ItemId::of)
                .toList();

        Map<ItemId, PriceDto> prices = priceRepository.getPricesForItemsWithIds(ids);

        List<SearchItem> searchItems = allFullItems
                .stream()
                .map(item -> convertToSearchItem(item, prices.get(ItemId.of(item.getId()))))
                .toList();
        enrichCategories(searchItems);
        return searchItems;
    }

    private void enrichCategories(List<SearchItem> items) {
        CategoryEnricher categoryEnricher = catalogFacade.getCategoryEnricher();
        for (SearchItem item : items) {
            Set<String> ids = new HashSet<>();
            item.categoryIds()
                    .forEach(id -> ids.addAll(
                            categoryEnricher.findAllCategoryIdsForId(UUID.fromString(id))
                                    .stream()
                                    .map(UUID::toString)
                                    .toList()));
            item.addAdditionalCategoryIds(ids);
        }
    }

    private SearchItem convertToSearchItem(FullItemDto item, PriceDto priceDto) {
        HashSet<String> categoryIds = new HashSet<>(item.getCategoryIds()
                .stream()
                .map(UUID::toString)
                .toList());
        return new SearchItem(
                item.getId(),
                item.getName(),
                item.getDescription(),
                priceDto.getFinalPrice()
                        .doubleValue(),
                priceDto.discounted(),
                item.getProducer()
                        .getId(),
                categoryIds);
    }
}
