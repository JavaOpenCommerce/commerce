package com.example.opencommerce.app.pricing.commands;

import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.Value;
import com.example.opencommerce.domain.Vat;

public record InitiateNewPriceCommand(ItemId id, Value priceNett, Vat vat) {
}
