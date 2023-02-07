package pl.sebastian.ideas100.service;

import org.springframework.stereotype.Service;
import pl.sebastian.ideas100.domain.model.Answer;
import pl.sebastian.ideas100.domain.model.Category;
import pl.sebastian.ideas100.domain.model.Question;
import pl.sebastian.ideas100.exception.NoContentException;

import java.util.*;
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

    public Optional<Answer> getAnswer(UUID id) {
        return getAnswers().stream()
                .filter(x -> x.getId().equals(id))
                .findFirst();
    }

    public Answer updateAnswer(UUID id, Answer answer) {
        Optional<Answer> answerOld = getAnswer(id);
        if (answerOld.isPresent()) {
            answersMock.remove(answerOld.get());
            answersMock.add(answer);
        } else {
            answersMock.add(answer);
        }
        answer.setId(id);

        return answer;

    }

    public void removeAnswer(UUID id) {
        Optional<Answer> answer = getAnswer(id);

        if (answer.isPresent()) {
            answersMock.remove(answer.get());
        } else {
            throw new NoContentException();
        }
    }

    public Answer addAnswer(Answer answer) {
        answersMock.add(answer);

        return answer;
    }
}
