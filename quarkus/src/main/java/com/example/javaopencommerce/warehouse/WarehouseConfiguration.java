package com.example.javaopencommerce.warehouse;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

class WarehouseConfiguration {

    @Produces
    @ApplicationScoped
    ItemMapper itemMapper() {
        return new ItemMapper();
    }

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
