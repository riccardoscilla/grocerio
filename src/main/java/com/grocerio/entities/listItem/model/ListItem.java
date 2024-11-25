package com.grocerio.entities.listItem.model;

import com.grocerio.entities.item.model.Item;
import com.grocerio.entities.shelf.model.Shelf;
import com.grocerio.entities.shelfItem.model.ShelfItemVm;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Entity
public class ListItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotNull
    @Min(1)
    public Long quantity;

    @NotNull
    public Instant insertionDate;

    public String note;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    public Item item; // List Item refers to one item

    public ListItemVm toVm() {
        return new ListItemVm(
                id,
                quantity,
                insertionDate,
                note,
                item.toVm()
        );
    }
}
