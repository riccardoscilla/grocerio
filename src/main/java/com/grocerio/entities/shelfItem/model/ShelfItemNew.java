package com.grocerio.entities.shelfItem.model;

import com.grocerio.entities.item.model.ItemNew;
import com.grocerio.entities.listItem.model.ListItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public class ShelfItemNew {
    @NotNull
    public ItemNew itemNew;

    @NotNull
    @Min(1)
    public Long quantity;

    @NotNull
    public Instant purchaseDate;

    public String note;

    public static ShelfItemNew from(ListItem listItem) {
        ShelfItemNew shelfItemNew = new ShelfItemNew();
        shelfItemNew.itemNew = ItemNew.from(listItem.item);
        shelfItemNew.quantity = listItem.quantity;
        shelfItemNew.purchaseDate = Instant.now();
        shelfItemNew.note = listItem.note;

        return shelfItemNew;
    }
}
