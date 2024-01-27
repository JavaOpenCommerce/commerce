package com.example.opencommerce.app.pricing;

import com.example.opencommerce.domain.pricing.PriceEventStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;


class PricingConfiguration {

    @Produces
    @ApplicationScoped
    InitiateNewItemPriceScenario initiateItemPriceScenario(PriceEventStore store) {
        return new InitiateNewItemPriceScenario(store);
    }

    @Produces
    @ApplicationScoped
    ChangeItemPriceScenario changeItemPriceScenario(PriceEventStore store) {
        return new ChangeItemPriceScenario(store);
    }

    @Produces
    @ApplicationScoped
    GetPriceForItemScenario getPriceForItemScenario(PriceEventStore store) {
        return new GetPriceForItemScenario(store);
    }

    @Produces
    @ApplicationScoped
    DiscountScenarios applyDiscountScenario(PriceEventStore store) {
        return new DiscountScenarios(store);
    }
}
