package com.grocerio.entities.shelfItem.model;

import com.grocerio.entities.item.model.ItemNew;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public class ShelfItemNew {
    @NotNull
    public ItemNew itemNew;

    @NotNull
    @Min(1)
    public Long quantity;

    @NotNull
    public Instant purchaseDate;

    public String note;
}
