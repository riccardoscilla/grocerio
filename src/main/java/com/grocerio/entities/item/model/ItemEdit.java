package com.grocerio.entities.item.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public class ItemEdit {
    @NotNull
    public Long id;

    @NotBlank
    public String name;

    @NotNull
    public Long categoryId;

    @NotNull
    public Boolean favourite;

    public Instant lastPurchaseDate;

    public static ItemEdit from(Item item) {
        ItemEdit itemEdit = new ItemEdit();
        itemEdit.id = item.id;
        itemEdit.name = item.name;
        itemEdit.categoryId = item.category.id;
        itemEdit.favourite = item.favourite;
        itemEdit.lastPurchaseDate = item.lastPurchaseDate;

        return itemEdit;
    }

    public static ItemEdit from(Item item, ItemNew itemNew) {
        ItemEdit itemEdit = new ItemEdit();
        itemEdit.id = item.id;
        itemEdit.name = itemNew.name;
        itemEdit.categoryId = itemNew.categoryId;
        itemEdit.favourite = itemNew.favourite;
        itemEdit.lastPurchaseDate = item.lastPurchaseDate;

        return itemEdit;
    }
}
