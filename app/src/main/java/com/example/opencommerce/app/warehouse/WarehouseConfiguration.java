package com.example.opencommerce.app.warehouse;

import com.example.opencommerce.domain.warehouse.WarehouseItemRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

class WarehouseConfiguration {

    @Produces
    @ApplicationScoped
    AddStockScenario addStockScenario(WarehouseItemRepository itemRepository) {
        return new AddStockScenario(itemRepository);
    }

    @Produces
    @ApplicationScoped
    ReserveItemScenario itemReservationScenario(WarehouseItemRepository itemRepository) {
        return new ReserveItemScenario(itemRepository);
    }

    @Produces
    ExecuteItemReservationScenario executeItemReservationScenario(WarehouseItemRepository itemRepository) {
        return new ExecuteItemReservationScenario(itemRepository);
    }
}
