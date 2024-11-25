package com.grocerio.entities.user.model;

import com.grocerio.entities.shelf.model.Shelf;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    public UUID uuid;

    @ManyToOne
    @JoinColumn(name = "shelf_id", nullable = false)
    public Shelf shelf; // Item belongs to one shelf

}
