package pl.sebastian.ideas100.question.service;

import org.springframework.stereotype.Service;
import pl.sebastian.ideas100.category.model.Category;
import pl.sebastian.ideas100.question.model.Question;
import pl.sebastian.ideas100.exception.NoContentException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    private List<Question> questionsMock = new ArrayList<>(Arrays.asList(new Question(new Category("życie"), "Jak żyć?"),
            new Question(new Category("sport"), "Kto wygrał Ligę Mistrzów w 2020 roku?")));

    public List<Question> getQuestions() {
        if (questionsMock.isEmpty()) {
            throw new NoContentException();
        }
        return questionsMock;
    }

    public List<Question> getQuestionsFromCategory(UUID categoryId) {
//        List<Question> questions = getQuestions().stream()
//                .filter(question -> question.getCategory().getId().equals(categoryId))
//                .toList();
        List<Question> questions = List.of(getQuestions().get(0));
//
//

        if (questions.isEmpty()) {
            throw new NoContentException();
        }
        return questions;
    }

    public Optional<Question> getQuestion(UUID id) {
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
        } else {
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
        Optional<UUID> id = Optional.ofNullable(question.getId());

        if (id.isEmpty()) {
            UUID newID = UUID.randomUUID();
            question.setId(newID);
        }

        questionsMock.add(question);

        return question;
    }
}
