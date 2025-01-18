package com.grocerio.entities.listItem.model;

import com.grocerio.entities.item.model.ItemNew;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public class ListItemNew {
    @NotNull
    public ItemNew itemNew;

    @NotNull
    @Min(1)
    public Long quantity;

    @NotNull
    public Instant insertionDate;

    public String note;
}
