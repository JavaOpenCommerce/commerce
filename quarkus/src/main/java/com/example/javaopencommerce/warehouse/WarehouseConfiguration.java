package com.example.javaopencommerce.warehouse;

import com.example.javaopencommerce.warehouse.query.WarehouseQueryRepository;

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

    @Produces
    @ApplicationScoped
    WarehouseController warehouseController(AddStockScenario addStockScenario, WarehouseQueryRepository queryRepository) {
        return new WarehouseController(addStockScenario, queryRepository);
    }
}
