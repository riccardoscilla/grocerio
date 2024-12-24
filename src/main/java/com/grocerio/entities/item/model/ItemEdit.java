package com.grocerio.entities.item.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ItemEdit {
    @NotNull
    public Long id;

    @NotBlank
    public String name;

    @NotNull
    public Long categoryId;
}
