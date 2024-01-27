package com.example.opencommerce.infra.pricing;

import com.example.opencommerce.app.pricing.DiscountScenarios;
import com.example.opencommerce.app.pricing.InitiateNewItemPriceScenario;
import com.example.opencommerce.app.pricing.commands.ApplyDiscountCommand;
import com.example.opencommerce.app.pricing.commands.InitiateNewPriceCommand;
import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.Value;
import com.example.opencommerce.domain.Vat;
import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@ApplicationScoped
public class TestPriceBootloader {

    private final InitiateNewItemPriceScenario initiatePrice;
    private final DiscountScenarios discounts;

    public TestPriceBootloader(InitiateNewItemPriceScenario initiatePrice, DiscountScenarios discounts) {
        this.initiatePrice = initiatePrice;
        this.discounts = discounts;
    }

    void initiateTestPrices(@Observes @Priority(1) StartupEvent ev) {
        log.info("Populating test price data...");
        Vat vat = Vat.of(0.23);

        // ItemId 1
        initiatePrice.initiateNewPrice(new InitiateNewPriceCommand(ItemId.of(1L), Value.of(11.99), vat));

        // ItemId 2
        initiatePrice.initiateNewPrice(new InitiateNewPriceCommand(ItemId.of(2L), Value.of(18999.99), vat));
        discounts.applyDiscount(new ApplyDiscountCommand(ItemId.of(2L), Value.of(1000.00), Instant.now()));

        // ItemId 3
        initiatePrice.initiateNewPrice(new InitiateNewPriceCommand(ItemId.of(3L), Value.of(7999.99), vat));

        // ItemId 4
        initiatePrice.initiateNewPrice(new InitiateNewPriceCommand(ItemId.of(4L), Value.of(4299.99), vat));

        // ItemId 5
        initiatePrice.initiateNewPrice(new InitiateNewPriceCommand(ItemId.of(5L), Value.of(4399.99), vat));

        // ItemId 6
        initiatePrice.initiateNewPrice(new InitiateNewPriceCommand(ItemId.of(6L), Value.of(6999.99), vat));
        discounts.applyDiscount(new ApplyDiscountCommand(ItemId.of(6L), Value.of(200.00), Instant.now()));

        // ItemId 7
        initiatePrice.initiateNewPrice(new InitiateNewPriceCommand(ItemId.of(7L), Value.of(3999.99), vat));
    }
}
