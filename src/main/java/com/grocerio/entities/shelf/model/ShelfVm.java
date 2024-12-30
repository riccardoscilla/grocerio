package com.grocerio.entities.shelf.model;

import com.grocerio.entities.category.model.Category;
import com.grocerio.entities.item.model.Item;
import com.grocerio.entities.listItem.model.ListItemVm;
import com.grocerio.entities.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
public class ShelfVm {
    public Long id;
    public String name;
    public String shareId;
}
