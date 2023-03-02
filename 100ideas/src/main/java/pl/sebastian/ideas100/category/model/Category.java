package pl.sebastian.ideas100.category.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "categories")
@Data
public class Category {

    @Id
    private UUID id;

    @NotBlank(message = "{ideas.validation.constraints.NotBlank.message}")
    @Size(min=5, max=255, message = "{ideas.validation.constraints.Size.message}")
    private String name;

    public Category(String name) {
        this();
        this.name = name;
    }

    public Category() {
        this.id = UUID.randomUUID();
    }

}

