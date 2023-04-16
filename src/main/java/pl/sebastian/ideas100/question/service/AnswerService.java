package pl.sebastian.ideas100.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.sebastian.ideas100.question.dto.AnswerStatDto;
import pl.sebastian.ideas100.question.model.Answer;
import pl.sebastian.ideas100.question.repository.AnswerRepository;
import pl.sebastian.ideas100.question.model.Question;
import pl.sebastian.ideas100.question.repository.QuestionRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    private final QuestionRepository questionRepository;

    private final AnswerStatMapper answerStatMapper;


    @Transactional(readOnly = true)
    public List<AnswerStatDto> getAnswers() {
        return answerRepository.findAll()
                .stream()
                .map(answerStatMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AnswerStatDto> getAnswersByQuestionId(UUID questionId) {
            return answerRepository.findAllByQuestionId(questionId)
                    .stream()
                    .map(answerStatMapper::map)
                    .collect(Collectors.toList());

    }
    @Transactional(readOnly = true)
    public AnswerStatDto getAnswer(UUID id) {
        Answer answer = answerRepository.getById(id);

        return answerStatMapper.map(answer);

    }

    @Transactional
    public AnswerStatDto updateAnswer(UUID answerId, AnswerStatDto answer) {
        Answer oldAnswer = answerRepository.getById(answerId);

        oldAnswer.setContent(answer.getContent());

        answerRepository.save(oldAnswer);

        return answerStatMapper.map(oldAnswer);

    }

    @Transactional
    public void removeAnswer(UUID answerId) {
        answerRepository.deleteById(answerId);
    }

    @Transactional
    public AnswerStatDto addAnswer(UUID questionId, AnswerStatDto answer) {
        Answer newAnswer = new Answer();
        Question question = questionRepository.getById(questionId);

        newAnswer.setContent(answer.getContent());
        question.addAnswer(newAnswer);

        answerRepository.save(newAnswer);
        questionRepository.save(question);

        return answerStatMapper.map(newAnswer);

    }
}
