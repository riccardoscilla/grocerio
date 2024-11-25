package com.grocerio.entities.listItem.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public class ListItemNew {
    @NotNull
    public Long itemId;

    @NotNull
    @Min(1)
    public Long quantity;

    @NotNull
    public Instant insertionDate;

    public String note;
}
