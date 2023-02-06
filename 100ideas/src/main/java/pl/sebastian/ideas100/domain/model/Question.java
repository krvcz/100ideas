package pl.sebastian.ideas100.model;


public class Question {


    private String category;

    private String content;

    public Question(String category, String content) {
        this.category = category;
        this.content = content;
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

    public Question() {
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

