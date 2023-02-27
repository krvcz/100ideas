package pl.sebastian.ideas100.category.model;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    private UUID id;
    private String name;

    public Category(String name) {
        this();
        this.name = name;
    }

    public Category() {
        this.id = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return this.name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

