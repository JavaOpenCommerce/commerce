package com.example.opencommerce.adapters.database.pricing.sql;

import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.pricing.ItemPrice;
import com.example.opencommerce.domain.pricing.PriceEventStore;
import com.example.opencommerce.domain.pricing.events.PriceEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
class SqlPriceEventStore implements PriceEventStore {

    private final PsqlPriceEventRepository repository;
    private final ObjectMapper mapper;

    public SqlPriceEventStore(PsqlPriceEventRepository repository, ObjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void saveEvent(ItemId id, PriceEvent event) {
        PriceEventEntity entity = new PriceEventEntity();
        entity.setId(UUID.randomUUID());
        entity.setItemId(id.asLong());
        entity.setData(toJson(event));
        entity.setType(event.getType());
        entity.setValidFrom(event.getValidFrom());
        repository.savePriceEvent(entity);
    }

    @Override
    public ItemPrice getPriceByItemId(ItemId id) {
        Instant now = Instant.now();
        List<PriceEventEntity> events = repository.getPriceEventsById(id.asLong());
        List<PriceEvent> history = events.stream()
                .filter(event -> event.getValidFrom()
                        .isBefore(now))
                .sorted(Comparator.comparing(PriceEventEntity::getIssuedAt)) // by issued or validFrom? To brainstorm
                .map(this::fromJson)
                .toList();

        ItemPrice itemPrice = ItemPrice.create(id);
        itemPrice.when(history);
        return itemPrice;
    }

    private PriceEvent fromJson(PriceEventEntity event) {
        try {
            return (PriceEvent) mapper.readValue(event.getData(), Class.forName(event.getType()));
        } catch (JsonProcessingException | ClassNotFoundException e) {
            throw new RuntimeException(String.format("Error while deserializing event of type %s.", event.getType()), e);
        }
    }

    private String toJson(PriceEvent event) {
        try {
            return mapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("Error while serializing event of type %s.", event.getType()), e);
        }
    }
}
