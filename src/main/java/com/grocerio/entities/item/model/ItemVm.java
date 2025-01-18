package com.grocerio.entities.item.model;

import com.grocerio.entities.category.model.CategoryVm;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ItemVm {
    public Long id;
    public String name;
    public CategoryVm category;
    public Boolean favourite;
}
