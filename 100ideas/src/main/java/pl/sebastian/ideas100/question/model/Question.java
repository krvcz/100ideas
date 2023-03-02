package pl.sebastian.ideas100.question.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import pl.sebastian.ideas100.answer.model.Answer;
import pl.sebastian.ideas100.category.model.Category;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table( name = "questions")
public class Question {

    @Id
    private UUID id;

    @OneToMany(mappedBy = "question")
    private Set<Answer> answers;


    private String content;

    @ManyToOne
    private Category category;

    public Question(Category category, String content) {
        this();
        this.category = category;
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

    public Set<Answer> getAnswers() {
        return Collections.unmodifiableSet(answers);
    }

    @Override
    public String toString() {
        return "Question{" +
                "category='" + category + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Category getCategory() {
        return category;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

