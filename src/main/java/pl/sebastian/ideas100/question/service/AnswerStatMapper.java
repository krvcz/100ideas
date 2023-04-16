package pl.sebastian.ideas100.question.service;


import org.springframework.stereotype.Component;
import pl.sebastian.ideas100.question.dto.AnswerStatDto;
import pl.sebastian.ideas100.question.model.Answer;
import pl.sebastian.ideas100.question.model.Question;

import java.util.Optional;

@Component
public class AnswerStatMapper {

    public AnswerStatDto map(Answer answer) {
        AnswerStatDto answerStatDto = new AnswerStatDto();

        answerStatDto.setId(answer.getId());
        answerStatDto.setContent(answer.getContent());

        Optional<Question> questionInAnswer = Optional.ofNullable(answer.getQuestion());

        questionInAnswer.ifPresent((question) -> {
            answerStatDto.setQuestion(answer.getQuestion().getContent());
            answerStatDto.setQuestionId(answer.getQuestion().getId());
        } );

        return answerStatDto;

    }
}
