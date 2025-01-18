package com.grocerio.entities.listItem.model;

import com.grocerio.entities.item.model.ItemNew;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public class ListItemEdit {
    @NotNull
    public Long id;

    @NotNull
    public ItemNew itemNew;

    @NotNull
    @Min(1)
    public Long quantity;

    @NotNull
    public Instant insertionDate;

    public String note;

    public static ListItemEdit from(ListItem listItem, ListItemNew listItemNew) {
        ListItemEdit listItemEdit = new ListItemEdit();
        listItemEdit.id = listItem.id;
        listItemEdit.itemNew = ItemNew.from(listItem.item);
        listItemEdit.quantity = listItem.quantity + listItemNew.quantity;
        listItemEdit.insertionDate = listItem.insertionDate;

        listItemEdit.note = listItem.note;
        if (listItemNew.note != null && !listItemNew.note.isBlank())
            listItemEdit.note += "\n" + listItemNew.note;
        return listItemEdit;
    }
}
