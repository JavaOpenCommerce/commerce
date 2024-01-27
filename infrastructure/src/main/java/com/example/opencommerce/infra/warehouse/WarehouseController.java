package com.example.opencommerce.infra.warehouse;

import com.example.opencommerce.app.warehouse.AddStockScenario;
import com.example.opencommerce.app.warehouse.query.ItemStockDto;
import com.example.opencommerce.app.warehouse.query.WarehouseQueryRepository;
import com.example.opencommerce.domain.Amount;
import com.example.opencommerce.domain.ItemId;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("items")
public class WarehouseController {

    private final AddStockScenario addStockScenario;
    private final WarehouseQueryRepository warehouseQueryRepository;

    public WarehouseController(AddStockScenario addStockScenario, WarehouseQueryRepository warehouseQueryRepository) {
        this.addStockScenario = addStockScenario;
        this.warehouseQueryRepository = warehouseQueryRepository;
    }

    @POST
    @Path("/{id}/warehouse/stock/{amount}")
    public Response addStockForItemWithId(@PathParam("id") Long id, @PathParam("amount") Integer amount) {
        addStockScenario.addToStock(ItemId.of(id), Amount.of(amount));
        return Response.ok()
                .build();
    }

    @GET
    @Path("/{id}/warehouse/stock")
    public Response getItemStock(@PathParam("id") Long id) {
        ItemStockDto itemStock = warehouseQueryRepository.getItemStockById(ItemId.of(id));
        return Response.ok(itemStock)
                .build();
    }
}
