package pl.sebastian.ideas100.category.model;

import java.util.UUID;

public class Category {
    private UUID id;
    private String name;

    public Category(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public Category() {
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

