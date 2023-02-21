package pl.sebastian.ideas100.domain.model;


import java.util.UUID;

public class Answer {
    private UUID id;
    private Question question;
    private String content;

    public Answer(Question question, String content) {
        this.id = UUID.randomUUID();
        this.question = question;
        this.content = content;
    }

    public Answer() {
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