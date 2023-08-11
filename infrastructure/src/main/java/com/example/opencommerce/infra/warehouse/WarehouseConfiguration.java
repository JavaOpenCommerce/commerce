package com.example.opencommerce.infra.warehouse;

import com.example.javaopencommerce.warehouse.AddStockScenario;
import com.example.javaopencommerce.warehouse.query.WarehouseQueryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

class WarehouseConfiguration {

    @Produces
    @ApplicationScoped
    WarehouseController warehouseController(AddStockScenario addStockScenario, WarehouseQueryRepository queryRepository) {
        return new WarehouseController(addStockScenario, queryRepository);
    }
}
