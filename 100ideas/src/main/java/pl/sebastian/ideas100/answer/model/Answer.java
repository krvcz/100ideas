package pl.sebastian.ideas100.answer.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import pl.sebastian.ideas100.question.model.Question;

import java.util.UUID;

@Entity
@Table(name = "answers")
public class Answer {

    @Id
    private UUID id;

    @ManyToOne
    private Question question;
    private String content;

    public Answer(Question question, String content) {
        this();
        this.question = question;
        this.content = content;
    }

    public Answer() {
        this.id = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return "Answer{" + "id=" + id + ", question=" + question + ", content='" + content + '\'' + '}';
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}