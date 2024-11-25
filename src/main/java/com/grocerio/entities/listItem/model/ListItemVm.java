package com.grocerio.entities.listItem.model;

import com.grocerio.entities.item.model.ItemVm;
import lombok.AllArgsConstructor;

import java.time.Instant;

@AllArgsConstructor

public class ListItemVm {
    public Long id;
    public Long quantity;
    public Instant insertionDate;
    public String note;
    public ItemVm item;
}
