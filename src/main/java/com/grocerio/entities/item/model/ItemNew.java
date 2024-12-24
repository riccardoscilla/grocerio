package com.grocerio.entities.item.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Set;

@AllArgsConstructor
public class ItemNew {
    @NotBlank
    public String name;

    @NotNull
    public Long categoryId;
}
