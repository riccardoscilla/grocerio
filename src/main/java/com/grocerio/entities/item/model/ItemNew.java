package com.grocerio.entities.item.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class ItemNew {
    @NotBlank
    public String name;

    @NotNull
    public Long categoryId;
}
