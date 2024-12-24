package com.grocerio.entities.listItem.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public class ListItemEdit {
    @NotNull
    public Long id;

    @NotBlank
    public String itemName;

    @NotNull
    public Long categoryId;

    @NotNull
    @Min(1)
    public Long quantity;

    @NotNull
    public Instant insertionDate;

    public String note;
}
