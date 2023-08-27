package com.example.opencommerce.app.pricing.commands;

import com.example.opencommerce.domain.ItemId;

import java.time.Instant;

public record RemoveDiscountCommand(ItemId id, Instant executionDate) {
}
