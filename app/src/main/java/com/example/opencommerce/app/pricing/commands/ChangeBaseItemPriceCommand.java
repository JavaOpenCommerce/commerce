package com.example.opencommerce.app.pricing.commands;

import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.Value;

import java.time.Instant;

public record ChangeBaseItemPriceCommand(ItemId id, Value newBasePrice, Instant executionDate) {
}
