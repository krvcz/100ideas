package pl.sebastian.ideas100.category.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import pl.sebastian.ideas100.question.model.Question;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "categories")
@Data
public class Category {

    @Id
    private UUID id;

    @NotBlank(message = "{ideas.validation.category.constraints.NotBlank.message}")
    @Size(min=5, max=255, message = "{ideas.validation.category.constraints.Size.message}")
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Question> questions;

    public Category(String name) {
        this();
        this.name = name;
    }

    public Category() {

        this.id = UUID.randomUUID();
        this.questions = new LinkedHashSet<>();
    }

}

