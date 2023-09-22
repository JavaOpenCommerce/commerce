package com.example.opencommerce.domain;

import java.time.Instant;

public abstract class Event {

    public String getType() {
        return this.getClass()
                .getName();
    }

    public abstract Instant getValidFrom();
}
