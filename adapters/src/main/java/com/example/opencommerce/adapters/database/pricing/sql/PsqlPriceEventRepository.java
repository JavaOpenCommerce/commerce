package com.example.opencommerce.adapters.database.pricing.sql;

import java.util.List;

interface PsqlPriceEventRepository {

    List<PriceEventEntity> getPriceEventsById(Long id);

    void savePriceEvent(PriceEventEntity entity);
}
