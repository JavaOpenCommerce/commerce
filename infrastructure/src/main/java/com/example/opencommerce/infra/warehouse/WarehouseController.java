package com.example.opencommerce.infra.warehouse;

import com.example.opencommerce.app.warehouse.AddStockScenario;
import com.example.opencommerce.app.warehouse.query.ItemStockDto;
import com.example.opencommerce.app.warehouse.query.WarehouseQueryRepository;
import com.example.opencommerce.domain.Amount;
import com.example.opencommerce.domain.ItemId;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

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
