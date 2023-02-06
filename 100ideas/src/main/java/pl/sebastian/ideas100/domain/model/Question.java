package pl.sebastian.ideas100.domain.model;


import java.util.Random;
import java.util.UUID;

public class Question {

    private UUID id;
    private String category;
    private String content;

    public Question(String category, String content) {
        this.id = UUID.randomUUID();
        this.category = category;
        this.content = content;
    }

    public Question() {
    }

    @Override
    public String toString() {
        return "Question{" +
                "category='" + category + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getCategory() {
        return category;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

