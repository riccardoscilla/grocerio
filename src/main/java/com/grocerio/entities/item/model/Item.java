package com.grocerio.entities.item.model;

import com.grocerio.entities.category.model.Category;
import com.grocerio.entities.category.model.CategoryVm;
import com.grocerio.entities.shelf.model.Shelf;
import com.grocerio.entities.shelfItem.model.ShelfItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "shelf_id"})
)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank
    public String name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    public Category category;  // Item has one category

    @ManyToOne
    @JoinColumn(name = "shelf_id", nullable = false)
    public Shelf shelf; // Item belongs to one shelf

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ShelfItem> shelfItems; // Item can refer to multiple items in shelf

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ShelfItem> listItems; // Item can refer to multiple items in list


    public ItemVm toVm() {
        return new ItemVm(
                id,
                name,
                category.toVm()
        );
    }
}

