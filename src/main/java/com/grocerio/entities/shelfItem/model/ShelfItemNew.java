package com.grocerio.entities.shelfItem.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public class ShelfItemNew {
    @NotNull
    public Long itemId;

    @NotNull
    @Min(1)
    public Long quantity;

    @NotNull
    public Instant purchaseDate;

    public String note;
}
