package com.grocerio.entities.category.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CategoryEdit {
    @NotNull
    public Long id;

    @NotBlank
    public String name;

    @NotBlank
    public String icon;
}
