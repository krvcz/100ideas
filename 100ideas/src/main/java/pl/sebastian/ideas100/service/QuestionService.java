package pl.sebastian.ideas100.service;

import org.springframework.stereotype.Service;
import pl.sebastian.ideas100.domain.model.Question;
import pl.sebastian.ideas100.exception.NoContentException;

import java.util.*;

@Service
public class QuestionService {
    private List<Question> questionsMock= new ArrayList<>(Arrays.asList(new Question("życie", "Jak żyć?"),
            new Question("sport", "Kto wygrał Ligę Mistrzów w 2020 roku?")));

    public List<Question> getQuestions(){
        if (questionsMock.isEmpty()) {
            throw new NoContentException();
        }
        return questionsMock;
    }

    public Optional<Question> getQuestion(UUID id){
        return getQuestions()
                .stream()
                .filter(x -> x.getId().equals(id))
                .findFirst();
    }

    public Question updateQuestion(UUID id, Question question) {
        Optional<Question> questionOld = getQuestion(id);
        if (questionOld.isPresent()) {
            questionsMock.remove(questionOld.get());
            questionsMock.add(question);
        }
        else {
            questionsMock.add(question);
        }
        question.setId(id);

        return question;

    }
    public void removeQuestion(UUID id) {
        Optional<Question> questionOld = getQuestion(id);

        if (questionOld.isPresent()) {
            questionsMock.remove(questionOld.get());
        } else {
            throw new NoContentException();
        }
    }

    public Question addQuestion(Question question) {
        questionsMock.add(question);

        return question;
    }
}
