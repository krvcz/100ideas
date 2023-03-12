package pl.sebastian.ideas100.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sebastian.ideas100.question.model.Question;
import pl.sebastian.ideas100.exception.NoContentException;
import pl.sebastian.ideas100.question.repository.QuestionRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public Page<Question> getQuestions(Pageable pageable) {
        return getQuestions(pageable, null);
    }

    @Transactional(readOnly = true)
    public Page<Question> getQuestions(Pageable pageable, String search) {

        if (search == null) {
            return questionRepository.findAll(pageable);
        } else {
            return getQuestionsFromQuery(search, pageable);
        }

    }

    @Transactional(readOnly = true)
    public List<Question> getQuestionsFromCategory(UUID categoryId) {
        return questionRepository.findAllByCategoryId(categoryId);
    }

    @Transactional(readOnly = true)
    public Optional<Question> getQuestion(UUID id) {
        return questionRepository.findById(id);
    }

    @Transactional
    public Question updateQuestion(UUID id, Question question) {
        Question oldQuestion = questionRepository.getById(id);

        oldQuestion.setCategory(question.getCategory());
        oldQuestion.setContent(question.getContent());

        return oldQuestion;
    }

    @Transactional
    public void removeQuestion(UUID id) {
        questionRepository.deleteById(id);
    }

    @Transactional
    public Question addQuestion(Question question) {
        Question newQuestion = new Question();

        newQuestion.setContent(question.getContent());
        newQuestion.setCategory(question.getCategory());

        questionRepository.save(newQuestion);

        return question;
    }

    @Transactional(readOnly = true)
    public Page<Question> getHotQuestions(Pageable pageable) {
        return questionRepository.findHot(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Question> getUnansweredQuestions(Pageable unansweredPage) {
        return questionRepository.getUnansweredQuestions(unansweredPage);
    }

    @Transactional(readOnly = true)
    public Page<Question> getQuestionsFromQuery(String query, Pageable pageable) {
        return questionRepository.findAllByQuery(query, pageable);
    }
}
