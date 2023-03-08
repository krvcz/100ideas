package pl.sebastian.ideas100.question.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sebastian.ideas100.question.model.Answer;
import pl.sebastian.ideas100.question.repository.AnswerRepository;
import pl.sebastian.ideas100.question.model.Question;
import pl.sebastian.ideas100.question.repository.QuestionRepository;

import java.util.*;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }
    @Transactional(readOnly = true)
    public List<Answer> getAnswers() {
        return answerRepository.findAll();
    }
    @Transactional(readOnly = true)
    public List<Answer> getAnswersByQuestionId(UUID questionId) {
            return answerRepository.findAllByQuestionId(questionId);

    }
    @Transactional(readOnly = true)
    public Optional<Answer> getAnswer(UUID id) {
        return answerRepository.findById(id);

    }

    @Transactional
    public Answer updateAnswer(UUID answerId, Answer answer) {
        Answer oldAnswer = answerRepository.getById(answerId);

        oldAnswer.setContent(answer.getContent());

        answerRepository.save(oldAnswer);

        return oldAnswer;

    }

    @Transactional
    public void removeAnswer(UUID answerId) {
        answerRepository.deleteById(answerId);
    }

    @Transactional
    public Answer addAnswer(UUID questionId, Answer answer) {
        Answer newAnswer = new Answer();
        Question question = questionRepository.getById(questionId);

        newAnswer.setContent(answer.getContent());
        question.addAnswer(answer);

        questionRepository.save(question);
        answerRepository.save(newAnswer);


        return newAnswer;
    }
}
