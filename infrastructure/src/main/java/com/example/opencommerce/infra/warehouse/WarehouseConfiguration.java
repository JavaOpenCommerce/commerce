package com.example.opencommerce.infra.warehouse;

import com.example.opencommerce.app.warehouse.AddStockScenario;
import com.example.opencommerce.app.warehouse.query.WarehouseQueryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

class WarehouseConfiguration {

    @Produces
    @ApplicationScoped
    WarehouseController warehouseController(AddStockScenario addStockScenario, WarehouseQueryRepository queryRepository) {
        return new WarehouseController(addStockScenario, queryRepository);
    }
}
