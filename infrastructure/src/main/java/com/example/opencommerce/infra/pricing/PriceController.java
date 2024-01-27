package com.example.opencommerce.infra.pricing;

import com.example.opencommerce.app.pricing.ChangeItemPriceScenario;
import com.example.opencommerce.app.pricing.DiscountScenarios;
import com.example.opencommerce.app.pricing.GetPriceForItemScenario;
import com.example.opencommerce.app.pricing.InitiateNewItemPriceScenario;
import com.example.opencommerce.app.pricing.commands.ApplyDiscountCommand;
import com.example.opencommerce.app.pricing.commands.ChangeBaseItemPriceCommand;
import com.example.opencommerce.app.pricing.commands.InitiateNewPriceCommand;
import com.example.opencommerce.app.pricing.commands.RemoveDiscountCommand;
import com.example.opencommerce.app.pricing.query.PriceDto;
import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.Value;
import com.example.opencommerce.domain.Vat;
import com.example.opencommerce.domain.pricing.PriceSnapshot;
import com.example.opencommerce.infra.pricing.requestdtos.ApplyDiscountRequest;
import com.example.opencommerce.infra.pricing.requestdtos.ChangePriceRequest;
import com.example.opencommerce.infra.pricing.requestdtos.InitiatePriceRequest;
import com.example.opencommerce.infra.pricing.requestdtos.RemoveDiscountRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("price")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PriceController {

    private final InitiateNewItemPriceScenario initiate;
    private final ChangeItemPriceScenario change;
    private final GetPriceForItemScenario get;
    private final DiscountScenarios discounts;


    public PriceController(InitiateNewItemPriceScenario initiate, ChangeItemPriceScenario change, GetPriceForItemScenario get, DiscountScenarios discounts) {
        this.initiate = initiate;
        this.change = change;
        this.get = get;
        this.discounts = discounts;
    }

    @GET
    @Path("/{id}")
    public PriceDto getItemPrice(@PathParam("id") Long id) {
        PriceSnapshot price = get.getPriceForItemWithId(ItemId.of(id));
        return PriceDto.fromSnapshot(price);
    }

    @POST
    @Path("/{id}/change")
    public Response changeBaseItemPrice(@PathParam("id") Long id, ChangePriceRequest request) {
        ChangeBaseItemPriceCommand command =
                new ChangeBaseItemPriceCommand(ItemId.of(id), Value.of(request.getNewPrice()), request.getValidFrom());
        change.changeBaseItemPrice(command);
        return Response.ok()
                .build();
    }

    @POST
    @Path("/{id}/init")
    public Response initiatePrice(@PathParam("id") Long id, InitiatePriceRequest request) {
        InitiateNewPriceCommand command =
                new InitiateNewPriceCommand(ItemId.of(id), Value.of(request.getBasePrice()), Vat.of(request.getVat()));
        initiate.initiateNewPrice(command);
        return Response.ok()
                .build();
    }

    @POST
    @Path("/{id}/discount")
    public Response applyDiscount(@PathParam("id") Long id, ApplyDiscountRequest request) {
        ApplyDiscountCommand command =
                new ApplyDiscountCommand(ItemId.of(id), Value.of(request.getDiscount()), request.getValidFrom());
        discounts.applyDiscount(command);
        return Response.ok()
                .build();
    }

    @POST
    @Path("/{id}/discount/remove")
    public Response removeDiscount(@PathParam("id") Long id, RemoveDiscountRequest request) {
        RemoveDiscountCommand command = new RemoveDiscountCommand(ItemId.of(id), request.getValidFrom());
        discounts.removeDiscount(command);
        return Response.ok()
                .build();
    }
}
