package pl.sebastian.ideas100.question.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.sebastian.ideas100.category.model.Category;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table( name = "questions")
@Getter
@Setter
@ToString
public class Question {

    @Id
    private UUID id;

    @OneToMany(mappedBy = "question")
    private Set<Answer> answers;

    @NotBlank(message = "{ideas.validation.question.constraints.NotNull.message}")
    @Size(min = 10, max=255, message = "{ideas.validation.question.constraints.Size.message}")
    private String content;

    @ManyToOne
    @NotNull(message = "{ideas.validation.question.constraints.NotNull.message}")
    private Category category;

    public Question(String content) {
        this();
        this.content = content;
    }

    public Question() {
        this.id = UUID.randomUUID();
    }


    public Question addAnswer(Answer answer) {
        if (answers == null) {
            answers = new LinkedHashSet<>();
        }

        answer.setQuestion(this);
        answers.add(answer);

        return this;
    }

}

