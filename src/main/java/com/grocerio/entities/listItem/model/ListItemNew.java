package com.grocerio.entities.listItem.model;

import com.grocerio.entities.item.model.ItemNew;
import com.grocerio.entities.shelfItem.model.ShelfItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public class ListItemNew {
    @NotNull
    public ItemNew itemNew;

    @NotNull
    @Min(1)
    public Long quantity;

    @NotNull
    public Instant insertionDate;

    public String note;

    @NotNull
    public Boolean inCart;

    public static ListItemNew from(ShelfItem shelfItem) {
        ListItemNew listItemNew = new ListItemNew();
        listItemNew.itemNew = ItemNew.from(shelfItem.item);
        listItemNew.quantity = 1L;
        listItemNew.insertionDate = Instant.now();
        listItemNew.note = shelfItem.note;
        listItemNew.inCart = false;

        return listItemNew;
    }

}
