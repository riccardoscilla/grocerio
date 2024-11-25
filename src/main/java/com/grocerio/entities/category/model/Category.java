package com.grocerio.entities.category.model;

import com.grocerio.entities.item.model.Item;
import com.grocerio.entities.item.model.ItemVm;
import com.grocerio.entities.shelf.model.Shelf;
import jakarta.persistence.*;
import java.util.Set;
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    public String icon;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Item> items; // A category can be used for multiple items

    @ManyToOne
    @JoinColumn(name = "shelf_id", nullable = false)
    public Shelf shelf; // Category belongs to one shelf

    // Getters and setters

    public CategoryVm toVm() {
        return new CategoryVm(
                id,
                name,
                icon
        );
    }
}
