package com.grocerio.entities.category.model;

import jakarta.validation.constraints.NotBlank;

public class CategoryNew {
    @NotBlank
    public String name;

    @NotBlank
    public String icon;
}
