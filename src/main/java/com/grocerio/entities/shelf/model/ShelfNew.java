package com.grocerio.entities.shelf.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class ShelfNew {
    @NotBlank
    public String name;
}
