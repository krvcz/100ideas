package pl.sebastian.ideas100.service;

import org.springframework.stereotype.Service;
import pl.sebastian.ideas100.domain.model.Answer;
import pl.sebastian.ideas100.domain.model.Category;
import pl.sebastian.ideas100.domain.model.Question;
import pl.sebastian.ideas100.exception.NoContentException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnswerService {

    private List<Answer> answersMock = new ArrayList<>(Arrays.asList(
                                    new Answer(new Question(new Category("życie"), "Jak żyć?"), "szybko"),
                                    new Answer(new Question(new Category("sport"), "Kto wygrał Ligę Mistrzów w 2020 roku?"), "Real Madryt")));

    public List<Answer> getAnswers() {
        if (answersMock.isEmpty()) {
            throw new NoContentException();
        }
        return answersMock;
    }

    public List<Answer> getAnswersByQuestionId(UUID questionId) {
//        return getAnswers().stream()
//                .filter(x -> x.getQuestion().getId().equals(questionId))
//                .collect(Collectors.toList());
        return getAnswers();

    }

    public Optional<Answer> getAnswer(UUID id) {
        return getAnswers().stream()
                .filter(x -> x.getQuestion().getId().equals(id))
                .findFirst();

    }

    public Answer updateAnswer(UUID answerId, Answer answer) {
        Optional<Answer> oldAnswer = getAnswer(answerId);

        if (oldAnswer.isPresent()) {
            answersMock.remove(oldAnswer.get());
            answersMock.add(answer);
        } else {
            answersMock.add(answer);
        }
        answer.setId(answerId);

        return answer;

    }

    public void removeAnswer(UUID answerId) {
        Optional<Answer> oldAnswer = getAnswer(answerId);

        if (oldAnswer.isPresent()) {
            answersMock.remove(oldAnswer.get());
        } else {
            throw new NoContentException();
        }
    }

    public Answer addAnswer(Answer answer) {
        answersMock.add(answer);

        return answer;
    }
}
