package pl.sebastian.ideas100.question.service;

import org.springframework.stereotype.Component;
import pl.sebastian.ideas100.question.dto.QuestionStatDto;
import pl.sebastian.ideas100.question.model.Question;

@Component
public class QuestionWithStatsMapper {

    public QuestionStatDto map(Question question) {
        QuestionStatDto questionStatDto = new QuestionStatDto();

        questionStatDto.setId(question.getId());
        questionStatDto.setContent(question.getContent());
        questionStatDto.setAnswers(question.getAnswers().size());
        questionStatDto.setCategory(question.getCategory().getName());

        return questionStatDto;
    }


}
