package pl.sebastian.ideas100.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sebastian.ideas100.category.repository.CategoryRepository;
import pl.sebastian.ideas100.question.dto.QuestionStatDto;
import pl.sebastian.ideas100.question.model.Question;
import pl.sebastian.ideas100.exception.NoContentException;
import pl.sebastian.ideas100.question.repository.QuestionRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    private  final CategoryRepository categoryRepository;

    private final QuestionWithStatsMapper questionWithStatsMapper;

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
    public Page<Question> getQuestionsFromCategory(UUID categoryId, Pageable pageable) {
        return questionRepository.findAllByCategoryId(categoryId, pageable);
    }

    @Transactional(readOnly = true)
    public QuestionStatDto getQuestion(UUID id) {
        return questionWithStatsMapper.map(questionRepository.getById(id));
    }

    @Transactional
    public Question updateQuestion(UUID id, QuestionStatDto questionStatDto) {
        Question oldQuestion = questionRepository.getById(id);
        Question newQuestion = questionWithStatsMapper.map(questionStatDto);

        oldQuestion.setCategory(newQuestion.getCategory());
        oldQuestion.setContent(newQuestion.getContent());

        return oldQuestion;
    }

    @Transactional
    public void removeQuestion(UUID id) {
        questionRepository.deleteById(id);
    }

    @Transactional
    public Question addQuestion(QuestionStatDto questionStatDto) {
        Question newQuestion = new Question();

        Question question = questionWithStatsMapper.map(questionStatDto);

        newQuestion.setCategory(question.getCategory());
        newQuestion.setContent(question.getContent());


        questionRepository.save(newQuestion);

        return newQuestion;
    }

    @Transactional(readOnly = true)
    public Page<Question> getHotQuestions(Pageable pageable) {
        return questionRepository.findHot(pageable);
    }

    @Transactional(readOnly = true)
    public List<QuestionStatDto> getHotQuestionsFromCategory(UUID categoryId, int limit) {
        return questionRepository.findHotByCategoryId(categoryId, limit).stream()
                .map(questionWithStatsMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<Question> getUnansweredQuestions(Pageable unansweredPage) {
        return questionRepository.getUnansweredQuestions(unansweredPage);
    }

    @Transactional(readOnly = true)
    public Page<Question> getQuestionsFromQuery(String query, Pageable pageable) {
        return questionRepository.findAllByQuery(query, pageable);
    }

    @Transactional(readOnly = true)
    public List<QuestionStatDto> findRandom(int limit) {
        return questionRepository.findRandomQuestions(limit).stream()
                .map(questionWithStatsMapper::map)
                .collect(Collectors.toList());

    }
}
