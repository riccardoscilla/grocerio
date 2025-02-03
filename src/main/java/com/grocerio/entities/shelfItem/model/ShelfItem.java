package com.grocerio.entities.shelfItem.model;

import com.grocerio.entities.item.model.Item;
import com.grocerio.entities.item.model.ItemVm;
import com.grocerio.entities.shelf.model.Shelf;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Entity
public class ShelfItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotNull
    @Min(1)
    public Long quantity;

    @NotNull
    public Instant purchaseDate;

    public String note;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    public Item item; // Shelf Item refers to one item

    public ShelfItemVm toVm() {
        return new ShelfItemVm(
                id,
                quantity,
                purchaseDate,
                note,
                item.toVm()
        );
    }
}
