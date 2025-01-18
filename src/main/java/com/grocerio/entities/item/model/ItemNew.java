package com.grocerio.entities.item.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ItemNew {
    @NotBlank
    public String name;

    @NotNull
    public Long categoryId;

    @NotNull
    public Boolean favourite;

    public static ItemNew from(Item item) {
        ItemNew itemNew = new ItemNew();
        itemNew.name = item.name;
        itemNew.categoryId = item.category.id;
        itemNew.favourite = item.favourite;
        return itemNew;
    }
}
