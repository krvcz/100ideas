package pl.sebastian.ideas100.question.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.sebastian.ideas100.question.model.Question;

import java.util.UUID;

@Entity
@Table(name = "answers")
@Getter
@Setter
public class Answer {

    @Id
    private UUID id;

    @ManyToOne
    private Question question;

    private String content;

    public Answer(String content) {
        this();
        this.content = content;
    }

    public Answer() {
        this.id = UUID.randomUUID();
    }

}