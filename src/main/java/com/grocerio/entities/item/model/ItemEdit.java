package com.grocerio.entities.item.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ItemEdit {
    @NotNull
    public Long id;

    @NotBlank
    public String name;

    @NotNull
    public Long categoryId;

    @NotNull
    public Boolean favourite;

    public static ItemEdit from(Item item, ItemNew itemNew) {
        ItemEdit itemEdit = new ItemEdit();
        itemEdit.id = item.id;
        itemEdit.name = itemNew.name;
        itemEdit.categoryId = itemNew.categoryId;
        itemEdit.favourite = itemNew.favourite;

        return itemEdit;
    }
}
