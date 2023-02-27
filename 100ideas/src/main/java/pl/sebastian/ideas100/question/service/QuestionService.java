package pl.sebastian.ideas100.question.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sebastian.ideas100.question.model.Question;
import pl.sebastian.ideas100.exception.NoContentException;
import pl.sebastian.ideas100.question.repository.QuestionRepository;

import java.util.*;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;



    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Transactional(readOnly = true)
    public List<Question> getQuestions() {
        return questionRepository.findAll();
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
}
