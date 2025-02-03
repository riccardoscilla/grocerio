package com.grocerio.entities.item.model;

import com.grocerio.entities.category.model.CategoryVm;
import lombok.AllArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
public class ItemVm {
    public Long id;
    public String name;
    public CategoryVm category;
    public Boolean favourite;
    public Instant lastPurchaseDate;
}
