package com.grocerio.entities.shelfItem.model;

import com.grocerio.entities.listItem.model.ListItem;
import com.grocerio.entities.listItem.model.ListItemEdit;
import com.grocerio.entities.listItem.model.ListItemNew;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public class ShelfItemEdit {
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
    public Instant purchaseDate;

    public String note;

    public static ShelfItemEdit from(ShelfItem shelfItem, ShelfItemNew shelfItemNew) {
        ShelfItemEdit shelfItemEdit = new ShelfItemEdit();
        shelfItemEdit.id = shelfItem.id;
        shelfItemEdit.itemName = shelfItem.item.name;
        shelfItemEdit.categoryId = shelfItem.item.category.id;
        shelfItemEdit.quantity += shelfItemNew.quantity;
        shelfItemEdit.purchaseDate = shelfItem.purchaseDate;
        shelfItemEdit.note += "\n" + shelfItemNew.note;
        return shelfItemEdit;
    }
}
