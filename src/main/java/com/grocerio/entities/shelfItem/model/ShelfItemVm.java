package com.grocerio.entities.shelfItem.model;

import com.grocerio.entities.item.model.ItemVm;
import lombok.AllArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
public class ShelfItemVm {
    public Long id;
    public Long quantity;
    public Instant purchaseDate;
    public String note;
    public ItemVm item;
}
