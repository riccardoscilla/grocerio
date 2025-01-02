package com.grocerio.entities.shelf.model;

import com.grocerio.entities.category.model.Category;
import com.grocerio.entities.item.model.Item;
import com.grocerio.entities.listItem.model.ListItem;
import com.grocerio.entities.listItem.model.ListItemVm;
import com.grocerio.entities.shelfItem.model.ShelfItem;
import com.grocerio.entities.user.model.User;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
public class Shelf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    public String shareId;

    @OneToMany(mappedBy = "shelf", cascade = CascadeType.PERSIST)
    public Set<User> users; // A shelf can be of multiple users

    @OneToMany(mappedBy = "shelf", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Item> items; // A shelf can have multiple items

    @OneToMany(mappedBy = "shelf", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Category> categories; // A shelf can have multiple categories

    public ShelfVm toVm() {
        return new ShelfVm(
                id,
                name,
                shareId
        );
    }
}
